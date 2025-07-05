package com.barbersync.api.features.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AboutMeResponseDto {
    private String primerNombre;
    private String primerApellido;
    private String email;
}
