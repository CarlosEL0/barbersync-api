package com.barbersync.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Integer idUsuario;
    private String nombreCompleto;
    private String rol;
    private String token;
}
