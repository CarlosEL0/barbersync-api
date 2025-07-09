package com.barbersync.api.features.barbero.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BarberoEspecialidadResponse {


    private Integer idUsuario;
    private String nombreCompleto;
    private String correo;
    private List<String> especialidades;
}
