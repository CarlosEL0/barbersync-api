package com.barbersync.api.features.auth.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String contrasena;
}
