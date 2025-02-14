package com.pragma.bootcamp.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TechnicalMessage {

    INTERNAL_ERROR("500","Something went wrong, please try again", ""),
    INTERNAL_ERROR_IN_ADAPTERS("PRC501","Something went wrong in adapters, please try again", ""),
    INVALID_REQUEST("400", "Bad Request, please verify data", ""),
    INVALID_PARAMETERS(INVALID_REQUEST.getCode(), "Bad Parameters, please verify data", ""),
    INVALID_EMAIL("403", "Invalid email, please verify", "email"),
    INVALID_MESSAGE_ID("404", "Invalid Message ID, please verify", "messageId"),
    UNSUPPORTED_OPERATION("501", "Method not supported, please try again", ""),
    USER_CREATED("201", "User created successfully", ""),
    ADAPTER_RESPONSE_NOT_FOUND("404-0", "invalid email, please verify", ""),
    USER_ALREADY_EXISTS("400","El usuario ya está registrado." ,"" ),
    TECNOLOGIA_ALREADY_EXISTS("400", "La Tecnologia ya existe." ,"" ),
    INVALID_TECNOLOGIA("400", "La Tecnologia es invalida.", "com/pragma/capacidad"),
    TECNOLOGIA_CREATED("201", "Tecnologia created successfully", ""),
    INVALID_TECHNOLOGY_NAME("400", "Invalid Technology Name", "name"),
    NO_DATA_FOUND("404", "No data found", ""),
    CAPACIDAD_ALREADY_EXISTS("400", "La Capacidad ya existe." ,"" ),
    BOOTCAMP_ALREADY_EXISTS("400", "El bootcamp ya existe." ,"" ),
    BOOTCAMPS_NOT_FOUND("404", "No se encontraron bootcamps", ""),
    INVALID_CAPACIDADES("400", "Invalid Capacidades", "capacidadIds"),
    GET_ALL_ERROR("500", "Error al obtener todos los bootcamps", ""),;

    private final String code;
    private final String message;
    private final String param;
}