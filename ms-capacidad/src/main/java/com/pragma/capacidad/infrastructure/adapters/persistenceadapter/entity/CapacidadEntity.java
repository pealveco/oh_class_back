package com.pragma.capacidad.infrastructure.adapters.persistenceadapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@Table("capacidades")
public class CapacidadEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private Set<Long> tecnologiaIds;
}