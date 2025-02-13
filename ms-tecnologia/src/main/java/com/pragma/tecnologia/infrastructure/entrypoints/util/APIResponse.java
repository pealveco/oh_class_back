package com.pragma.tecnologia.infrastructure.entrypoints.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class APIResponse {
    private String code;
    private String message;
    private String identifier;
    private String date;
    private TecnologiaDTO data;
    private List<ErrorDTO> errors;
}
