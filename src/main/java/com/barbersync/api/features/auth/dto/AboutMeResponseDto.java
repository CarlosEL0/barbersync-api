package com.barbersync.api.features.auth.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AboutMeResponseDto {
    private String primerNombre;
    private String primerApellido;
    private String email;
}
