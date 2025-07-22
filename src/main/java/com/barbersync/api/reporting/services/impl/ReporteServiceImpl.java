package com.barbersync.api.reporting.services.impl;

import com.barbersync.api.features.cita.CitaRepository;
import com.barbersync.api.reporting.dto.DiaMasOcupadoDTO;
import com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO;
import com.barbersync.api.reporting.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final CitaRepository citaRepository;

    @Override
    public List<ServicioMasAgendadoDTO> getServiciosMasAgendados(LocalDate fechaInicio, LocalDate fechaFin) {
        // La lógica vendrá aquí. Decidirá si llama a la consulta de totales o a la de rango.
        return Collections.emptyList();
    }

    @Override
    public List<DiaMasOcupadoDTO> getDiasMasOcupados(LocalDate fechaInicio, LocalDate fechaFin) {
        // La lógica vendrá aquí. Llamará a la consulta y procesará los datos en Java.
        return Collections.emptyList();
    }
}