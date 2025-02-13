package com.pragma.tecnologia.infrastructure.entrypoints.handler;

import com.pragma.tecnologia.domain.api.TecnologiaServicePort;
import com.pragma.tecnologia.domain.enums.TechnicalMessage;
import com.pragma.tecnologia.domain.exceptions.BusinessException;
import com.pragma.tecnologia.domain.exceptions.TechnicalException;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.TecnologiaMapper;
import com.pragma.tecnologia.infrastructure.entrypoints.util.APIResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.time.Instant;
import java.util.List;

import static com.pragma.tecnologia.infrastructure.entrypoints.util.Constants.X_MESSAGE_ID;

@Component
@Slf4j
@RequiredArgsConstructor
public class TecnologiaHandlerImpl {

    private final TecnologiaServicePort tecnologiaServicePort;
    private final TecnologiaMapper tecnologiaMapper;

    public Mono<ServerResponse> createTecnologia(ServerRequest request) {
        String messageId = getMessageId(request);
        return request.bodyToMono(TecnologiaDTO.class)
                .flatMap(tecnologiaDTO -> {
                    if (tecnologiaDTO.name() == null || tecnologiaDTO.description() == null) {
                        return Mono.error(new BusinessException(TechnicalMessage.INVALID_PARAMETERS));
                    }
                    Tecnologia tecnologia = tecnologiaMapper.tecnologiaDTOToTecnologia(tecnologiaDTO);
                    return tecnologiaServicePort.registerTecnologia(tecnologia)
                            .doOnSuccess(savedTecnologia -> log.info("Tecnologia created successfully with messageId: {}", messageId));
                })
                .flatMap(savedTecnologia -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(savedTecnologia))
                .contextWrite(Context.of(X_MESSAGE_ID, messageId))
                .doOnError(ex -> log.error("Error creating tecnologia", ex))
                .onErrorResume(BusinessException.class, ex -> buildErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        messageId,
                        TechnicalMessage.INVALID_PARAMETERS,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())))
                .onErrorResume(TechnicalException.class, ex -> buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messageId,
                        TechnicalMessage.INTERNAL_ERROR,
                        List.of(ErrorDTO.builder()
                                .code(ex.getTechnicalMessage().getCode())
                                .message(ex.getTechnicalMessage().getMessage())
                                .param(ex.getTechnicalMessage().getParam())
                                .build())))
                .onErrorResume(ex -> {
                    log.error("Unexpected error occurred for messageId: {}", messageId, ex);
                    return buildErrorResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            messageId,
                            TechnicalMessage.INTERNAL_ERROR,
                            List.of(ErrorDTO.builder()
                                    .code(TechnicalMessage.INTERNAL_ERROR.getCode())
                                    .message(TechnicalMessage.INTERNAL_ERROR.getMessage())
                                    .build()));
                });
    }


    public Mono<ServerResponse> getAllTecnologias(ServerRequest request) {
        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("10"));
        String sort = request.queryParam("sort").orElse("asc");

        Sort.Direction direction = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "name"));

        return ServerResponse.ok().body(
            tecnologiaServicePort.getAllTecnologias(pageable)
                .map(tecnologia -> {
                    TecnologiaDTO dto = tecnologiaMapper.tecnologiaToTecnologiaDTO(tecnologia);
                    dto = new TecnologiaDTO(tecnologia.id(), dto.name(), dto.description());
                    return dto;
                }),
            TecnologiaDTO.class
        );
    }

    public Mono<ServerResponse> getTecnologiaById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return tecnologiaServicePort.getTecnologiaById(id)
                .flatMap(tecnologia ->
                        ServerResponse.ok().bodyValue(tecnologiaMapper.tecnologiaToTecnologiaDTO(tecnologia)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteTecnologia(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return tecnologiaServicePort.deleteTecnologiaById(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    private Mono<ServerResponse> buildErrorResponse(HttpStatus httpStatus, String identifier, TechnicalMessage error,
                                                    List<ErrorDTO> errors) {
        return Mono.defer(() -> {
            APIResponse apiErrorResponse = APIResponse
                    .builder()
                    .code(error.getCode())
                    .message(error.getMessage())
                    .identifier(identifier)
                    .date(Instant.now().toString())
                    .errors(errors)
                    .build();
            return ServerResponse.status(httpStatus)
                    .bodyValue(apiErrorResponse);
        });
    }

    private String getMessageId(ServerRequest serverRequest) {
        String messageId = serverRequest.headers().firstHeader(X_MESSAGE_ID);
        if (messageId == null || messageId.isBlank()) {
            messageId = "default-message-id";
        }
        return messageId;
    }

    public Mono<ServerResponse> updateTecnologia(ServerRequest request) {
        try {
            Long id = Long.valueOf(request.pathVariable("id"));
            return request.bodyToMono(TecnologiaDTO.class)
                    .flatMap(tecnologiaDTO -> {
                        if (tecnologiaDTO.name() == null || tecnologiaDTO.description() == null) {
                            return Mono.error(new BusinessException(TechnicalMessage.INVALID_PARAMETERS));
                        }
                        return tecnologiaServicePort.getTecnologiaById(id)
                                .flatMap(existingTecnologia -> {
                                    Tecnologia updatedTecnologia = new Tecnologia(
                                        existingTecnologia.id(),
                                        tecnologiaDTO.name(),
                                        tecnologiaDTO.description()
                                    );
                                    return tecnologiaServicePort.updateTecnologia(updatedTecnologia);
                                });
                    })
                    .flatMap(updatedTecnologia -> ServerResponse.ok().bodyValue(updatedTecnologia))
                    .onErrorResume(e -> {
                        if (e instanceof BusinessException) {
                            return buildErrorResponse(HttpStatus.BAD_REQUEST, getMessageId(request), ((BusinessException) e).getTechnicalMessage(), null);
                        }
                        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, getMessageId(request), TechnicalMessage.INTERNAL_ERROR, null);
                    });
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, getMessageId(request), TechnicalMessage.INTERNAL_ERROR, null);
        }
    }
}