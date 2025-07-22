package com.barbersync.api.reporting.services.impl;

import com.barbersync.api.features.cita.CitaRepository;
import com.barbersync.api.reporting.dto.DiaMasOcupadoDTO;
import com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO;
import com.barbersync.api.reporting.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final CitaRepository citaRepository;

    @Override
    public List<ServicioMasAgendadoDTO> getServiciosMasAgendados(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return citaRepository.findServiciosMasAgendados(fechaInicio, fechaFin);
        } else {
            return citaRepository.findServiciosMasAgendadosHistorico();
        }
    }

    @Override
    public List<DiaMasOcupadoDTO> getDiasMasOcupados(LocalDate fechaInicio, LocalDate fechaFin) {
        // Las fechas son obligatorias según la definición del controlador, así que no es necesario un null check.
        return citaRepository.findDiasMasOcupados(fechaInicio, fechaFin);
    }
}