package com.barbersync.api.features.barbero.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BarberoEspecialidadRequest {
    private Integer idUsuario;
    private List<Integer> idEspecialidades;
}
