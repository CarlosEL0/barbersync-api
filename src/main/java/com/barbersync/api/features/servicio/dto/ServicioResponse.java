package com.barbersync.api.features.servicio.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ServicioResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer duracionMinuto;
}
