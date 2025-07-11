package com.barbersync.api.auth.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String contrasena;
}
