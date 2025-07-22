package com.barbersync.api.reporting;

// --- Imports necesarios ---
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

@RestController // 1. Le dice a Spring que esta clase es un controlador de API REST.
@RequestMapping("/api/reportes") // 2. Define la URL base para todos los endpoints de esta clase.
@RequiredArgsConstructor // 3. Crea un constructor con los campos 'final' (para inyección de dependencias).
@PreAuthorize("hasRole('ADMIN')") // 4. Seguridad: Solo usuarios con el rol 'ADMIN' pueden acceder.
public class ReporteController {

    // 5. Inyecta la dependencia del servicio que contiene la lógica de negocio.
    private final ReporteService reporteService;

    /**
     * Endpoint para obtener un ranking de los servicios más agendados.
     * Puede ser histórico o en un rango de fechas.
     * URL: GET /api/reportes/servicios-mas-agendados?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/servicios-mas-agendados") // 6. Mapea peticiones GET a este método.
    public ResponseEntity<List<ServicioMasAgendadoDTO>> getServiciosMasAgendados(
            // 7. Parámetros opcionales de la URL para filtrar por fecha.
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        // 8. Llama al servicio para obtener los datos.
        List<ServicioMasAgendadoDTO> reporte = reporteService.getServiciosMasAgendados(fechaInicio, fechaFin);

        // 9. Devuelve los datos con un código de estado 200 OK.
        return ResponseEntity.ok(reporte);
    }

    /**
     * Endpoint para obtener los días de la semana con más citas en un rango de fechas.
     * URL: GET /api/reportes/dias-mas-ocupados?fechaInicio=...&fechaFin=...
     */
    @GetMapping("/dias-mas-ocupados")
    public ResponseEntity<List<DiaMasOcupadoDTO>> getDiasMasOcupados(
            // 10. Parámetros obligatorios de la URL para el rango de fechas.
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<DiaMasOcupadoDTO> reporte = reporteService.getDiasMasOcupados(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}