package com.barbersync.api.features.horario.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class HorarioRequest {
    private Integer idBarbero;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private Integer idTiempoSesion;
}
