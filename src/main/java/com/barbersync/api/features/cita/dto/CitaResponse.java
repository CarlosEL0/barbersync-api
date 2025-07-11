package com.barbersync.api.features.cita.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CitaResponse {
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;
    private Integer idCliente;
    private Integer idBarbero;
    private Integer duracionTotalMinutos;
    private String nombreCliente;      // ✅ nuevo
    private String nombreBarbero;      // ✅ nuevo
}

