package com.barbersync.api.features.rol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolRequest {
    @NotBlank(message = "El nombre del rol no puede estar vacío")
    private String nombre;
}
