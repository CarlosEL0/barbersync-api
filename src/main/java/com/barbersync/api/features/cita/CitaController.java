package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import com.barbersync.api.features.cita.services.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> crear(@RequestBody CitaRequest request) {
        return ResponseEntity.ok(citaService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CitaResponse>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponse> actualizar(@PathVariable Integer id, @RequestBody CitaRequest request) {
        return ResponseEntity.ok(citaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
