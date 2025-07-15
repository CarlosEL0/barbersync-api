package com.barbersync.api.features.disponibilidad;

import com.barbersync.api.features.disponibilidad.dto.HorarioDisponibleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping("/barbero/{idBarbero}")
    @PreAuthorize("isAuthenticated()") // Cualquier usuario logueado puede consultar
    public ResponseEntity<List<HorarioDisponibleDTO>> obtenerDisponibilidad(
            @PathVariable Integer idBarbero,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam List<Integer> idServicios) {

        List<HorarioDisponibleDTO> disponibilidad = disponibilidadService.calcularDisponibilidad(idBarbero, fecha, idServicios);
        return ResponseEntity.ok(disponibilidad);
    }
    @GetMapping("/check-me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> checkAuthentication(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("No autenticado.");
        }
        return ResponseEntity.ok(authentication);
    }
}