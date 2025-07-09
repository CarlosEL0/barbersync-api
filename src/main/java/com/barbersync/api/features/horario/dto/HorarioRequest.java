package com.barbersync.api.features.horario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HorarioRequest {
    @NotNull(message = "El ID del barbero es obligatorio")
    private Integer idBarbero;

    @NotNull(message = "La hora de entrada es obligatoria")
    private LocalTime horaEntrada;

    @NotNull(message = "La hora de salida es obligatoria")
    private LocalTime horaSalida;

    @NotNull(message = "El ID del tiempo de sesión es obligatorio")
    private Integer idTiempoSesion;
}
