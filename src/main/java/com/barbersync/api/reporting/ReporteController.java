package com.barbersync.api.reporting;

import com.barbersync.api.reporting.dto.DiaMasOcupadoDTO;
import com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO;
import com.barbersync.api.reporting.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
 // Mantenemos la seguridad a nivel de controlador para el ADMIN
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/servicios-mas-agendados")
    public ResponseEntity<List<ServicioMasAgendadoDTO>> getServiciosMasAgendados(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<ServicioMasAgendadoDTO> reporte = reporteService.getServiciosMasAgendados(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/dias-mas-ocupados")
    public ResponseEntity<List<DiaMasOcupadoDTO>> getDiasMasOcupados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<DiaMasOcupadoDTO> reporte = reporteService.getDiasMasOcupados(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}