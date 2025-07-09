package com.barbersync.api.features.rol.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RolResponse {
    private Integer id;
    private String rol;  // Cambiado de 'nombre' a 'rol'
}
