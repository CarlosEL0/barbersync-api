package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.dto.HorarioRequest;
import com.barbersync.api.features.horario.dto.HorarioResponse;
import com.barbersync.api.features.horario.services.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioResponse>> obtenerTodos() {
        return ResponseEntity.ok(horarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(horarioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<HorarioResponse> crear(@RequestBody HorarioRequest request) {
        return ResponseEntity.ok(horarioService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponse> actualizar(@PathVariable Integer id, @RequestBody HorarioRequest request) {
        return ResponseEntity.ok(horarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
