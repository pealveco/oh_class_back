package com.pragma.tecnologia.infrastructure.entrypoints.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;

    // Constructor con argumentos
    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Constructor sin argumentos
    public UserDTO() {
    }

    // Getters y setters
    // ...
}