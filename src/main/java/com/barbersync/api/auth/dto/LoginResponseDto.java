package com.barbersync.api.auth.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private Integer idUsuario;
    private String rol; // puede ser "cliente", "barbero", "admin"
    private String nombreCompleto;
    private String mensaje; // opcional, para éxito o error
}