package com.barbersync.api.features.barbero.dto;

import lombok.Data;
import java.util.List;

@Data
public class BarberoEspecialidadRequest {
    private Integer idUsuario;
    private List<Integer> idEspecialidades;
}
