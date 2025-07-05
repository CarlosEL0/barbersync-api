package com.barbersync.api.features.barbero.dto;

import lombok.Data;
import java.util.List;

@Data
public class BarberoResponseDto {
    private Integer id;
    private String nombreCompleto;
    private String correo;
    private List<String> especialidades;
}
