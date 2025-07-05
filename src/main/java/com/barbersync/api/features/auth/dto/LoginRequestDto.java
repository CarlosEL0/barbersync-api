package com.barbersync.api.features.auth.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String contrasena;
}
