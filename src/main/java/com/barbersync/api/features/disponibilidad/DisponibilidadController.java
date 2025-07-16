package com.barbersync.api.features.disponibilidad;

import com.barbersync.api.features.disponibilidad.dto.DisponibilidadRequest;
import com.barbersync.api.features.disponibilidad.dto.HorarioDisponibleDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping("/barbero/{idBarbero}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HorarioDisponibleDTO>> obtenerDisponibilidad(
            @PathVariable("idBarbero") Integer idBarbero,
            @RequestParam(name = "fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(name = "idServicios") List<Integer> idServicios) {

        List<HorarioDisponibleDTO> disponibilidad = disponibilidadService.calcularDisponibilidad(idBarbero, fecha, idServicios);
        return ResponseEntity.ok(disponibilidad);
    }
/*
    // 👇 MÉTODO NUEVO CON POST (AÑADIR) 👇
    @PostMapping("/barbero/{idBarbero}") // Cambiado a @PostMapping y ruta simplificada
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HorarioDisponibleDTO>> obtenerDisponibilidadConPost(
            @PathVariable("idBarbero") Integer idBarbero,
            @RequestBody DisponibilidadRequest request) { // Leemos del cuerpo de la petición

        // Obtenemos los datos del objeto DTO
        LocalDate fecha = request.getFecha();
        List<Integer> idServicios = request.getIdServicios();

        List<HorarioDisponibleDTO> disponibilidad = disponibilidadService.calcularDisponibilidad(idBarbero, fecha, idServicios);
        return ResponseEntity.ok(disponibilidad);
    }
*/
    @GetMapping("/check-me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> checkAuthentication(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("No autenticado.");
        }
        return ResponseEntity.ok(authentication);
    }

    /*
        @GetMapping("/debug/**")
        public ResponseEntity<Map<String, Object>> debugRequest(HttpServletRequest request) {
            // 1. Obtenemos el mapa de parámetros de la query string
            Map<String, String[]> parameterMap = request.getParameterMap();

            // 2. Obtenemos todas las cabeceras de la petición
            Map<String, String> headersMap = Collections.list(request.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(
                            headerName -> headerName,
                            request::getHeader
                    ));

            // 3. Creamos un objeto de respuesta con toda la información
            Map<String, Object> debugInfo = Map.of(
                    "requestURI", request.getRequestURI(),
                    "queryString", (request.getQueryString() == null) ? "NO_QUERY_STRING" : request.getQueryString(),
                    "method", request.getMethod(),
                    "parameters", parameterMap,
                    "headers", headersMap
            );

            // 4. Devolvemos la información como un JSON con status 200 OK
            return ResponseEntity.ok(debugInfo);
        }*/
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Controller funcionando");
    }
}