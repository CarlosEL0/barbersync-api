package com.barbersync.api.features.barbero.dto;

import lombok.Data;
import java.util.List;

@Data
public class BarberoEspecialidadResponse {
    private Integer idUsuario;
    private String nombreCompleto;
    private String correo;
    private List<String> especialidades;
}
