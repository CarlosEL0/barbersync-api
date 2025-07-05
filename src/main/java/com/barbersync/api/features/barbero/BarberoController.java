package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.dto.BarberoRequestDto;
import com.barbersync.api.features.barbero.dto.BarberoResponseDto;
import com.barbersync.api.features.barbero.services.BarberoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barberos")
@RequiredArgsConstructor
public class BarberoController {

    private final BarberoService barberoService;

    @GetMapping
    public ResponseEntity<List<BarberoResponseDto>> obtenerTodos() {
        return ResponseEntity.ok(barberoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(barberoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<BarberoResponseDto> crear(@RequestBody BarberoRequestDto request) {
        return ResponseEntity.ok(barberoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberoResponseDto> actualizar(@PathVariable Integer id, @RequestBody BarberoRequestDto request) {
        return ResponseEntity.ok(barberoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        barberoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
