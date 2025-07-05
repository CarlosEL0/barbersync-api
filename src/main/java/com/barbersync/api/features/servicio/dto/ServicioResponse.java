package com.barbersync.api.features.servicio.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicioResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer duracionMinuto;
}
