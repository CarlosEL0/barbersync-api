package com.barbersync.api.reporting.services;

import com.barbersync.api.reporting.dto.DiaMasOcupadoDTO;
import com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {

    /**
     * Obtiene un ranking de los servicios más agendados.
     * Si las fechas son nulas, devuelve el ranking histórico total.
     */
    List<ServicioMasAgendadoDTO> getServiciosMasAgendados(
            @Nullable LocalDate fechaInicio,
            @Nullable LocalDate fechaFin
    );

    /**
     * Obtiene un ranking de los días de la semana con más citas dentro de un período.
     * Las fechas son obligatorias.
     */
    List<DiaMasOcupadoDTO> getDiasMasOcupados(
            LocalDate fechaInicio,
            LocalDate fechaFin
    );
}