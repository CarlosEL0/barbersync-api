package com.barbersync.api.features.cita.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class CitaRequest {

    private Integer idCliente;
    private Integer idBarbero;
    private LocalDate fecha;
    private LocalTime hora;

}
