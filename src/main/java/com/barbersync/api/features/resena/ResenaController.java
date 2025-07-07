package com.barbersync.api.features.resena;

import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;
import com.barbersync.api.features.resena.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaResponse> crear(@RequestBody ResenaRequest request) {
        return ResponseEntity.ok(resenaService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ResenaResponse>> obtenerTodas() {
        return ResponseEntity.ok(resenaService.obtenerTodas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
