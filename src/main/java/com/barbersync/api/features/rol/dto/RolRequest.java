package com.barbersync.api.features.rol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RolRequest {
    @NotBlank(message = "El rol no puede estar vacío") // Cambiado el mensaje para coincidir con el nuevo campo
    private String rol;  // Cambiado de 'nombre' a 'rol'
}
