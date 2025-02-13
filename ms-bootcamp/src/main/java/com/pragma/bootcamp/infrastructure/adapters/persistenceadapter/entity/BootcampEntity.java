package com.pragma.bootcamp.infrastructure.adapters.persistenceadapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("bootcamps")
public class BootcampEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private List<Long> capacidadIds;
}