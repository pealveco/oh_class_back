package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TecnologiaDTO {

    private Long id;
    private String name;
    private String descripcion;
}
