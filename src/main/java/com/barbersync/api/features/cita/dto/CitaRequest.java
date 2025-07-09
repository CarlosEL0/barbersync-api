package com.barbersync.api.features.cita.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CitaRequest {

    @NotNull(message = "El ID del cliente es obligatorio.")
    private Integer idCliente;

    @NotNull(message = "El ID del barbero es obligatorio.")
    private Integer idBarbero;

    @NotNull(message = "El estado de la cita es obligatorio.")
    private Integer estadoCitaId;

    @NotNull(message = "La fecha de la cita es obligatoria.")
    private LocalDate fecha;

    @NotNull(message = "La hora de la cita es obligatoria.")
    private LocalTime hora;

    @NotNull(message = "La lista de servicios es obligatoria.")
    private List<Integer> idServicios; // ✅ nuevo campo importante
}
