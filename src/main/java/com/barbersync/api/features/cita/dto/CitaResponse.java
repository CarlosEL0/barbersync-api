package com.barbersync.api.features.cita.dto;

import com.barbersync.api.features.servicio.dto.ServicioResponse;
import lombok.*;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


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
    private List <ServicioResponse> servicios = new ArrayList<>();
    private String fechaHora;
}

