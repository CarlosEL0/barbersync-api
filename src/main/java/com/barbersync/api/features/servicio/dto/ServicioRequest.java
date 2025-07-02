package com.barbersync.api.features.servicio.dto;

import lombok.Data;

@Data

public class ServicioRequest {

    private String nombre;
    private String descripcion;
    private double precio;
    private Integer duracionMinuto;
}