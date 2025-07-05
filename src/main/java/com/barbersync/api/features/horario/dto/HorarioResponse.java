package com.barbersync.api.features.horario.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class HorarioResponse {
    private Integer id;
    private Integer idBarbero;
    private String nombreBarbero;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private String diaLaboral;
    private Integer intervaloSesionMinutos;
}
