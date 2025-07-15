package com.barbersync.api.features.resena;

import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;
import com.barbersync.api.features.resena.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ResenaResponse> crear(@RequestBody ResenaRequest request) {
        return ResponseEntity.ok(resenaService.crear(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @resenaSecurityService.estaInvolucrado(authentication, #id)")
    public ResponseEntity<ResenaResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.obtenerPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResenaResponse>> obtenerTodas() {
        return ResponseEntity.ok(resenaService.obtenerTodas());
    }

    @PutMapping("/{id}")
    @PreAuthorize("@resenaSecurityService.esPropietarioCliente(authentication, #id)")
    public ResponseEntity<ResenaResponse> actualizar(@PathVariable Integer id, @RequestBody ResenaRequest request) {
        return ResponseEntity.ok(resenaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/barbero/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<List<ResenaResponse>> obtenerPorBarbero(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.obtenerPorBarbero(id));
    }

    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasRole('ADMIN') or #idCliente == authentication.principal.id")
    public ResponseEntity<List<ResenaResponse>> obtenerPorCliente(@PathVariable("idCliente") Integer idCliente) {
        return ResponseEntity.ok(resenaService.obtenerPorCliente(idCliente));
    }

    @GetMapping("/barbero/{id}/promedio")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Double> obtenerPromedio(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.obtenerPromedioCalificacion(id));
    }

    @GetMapping("/barbero/{id}/fechas")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<List<ResenaResponse>> obtenerPorFechas(
            @PathVariable Integer id,
            @RequestParam String inicio,
            @RequestParam String fin) {

        LocalDate fechaInicio = LocalDate.parse(inicio);
        LocalDate fechaFin = LocalDate.parse(fin);

        return ResponseEntity.ok(resenaService.obtenerPorBarberoEntreFechas(id, fechaInicio, fechaFin));
    }
}