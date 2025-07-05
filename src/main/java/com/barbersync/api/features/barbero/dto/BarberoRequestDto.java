package com.barbersync.api.features.barbero.dto;

import lombok.Data;
import java.util.List;

@Data
public class BarberoRequestDto {
    private Integer idUsuario; // ID del usuario base
    private List<Integer> especialidades; // IDs de especialidades
}
