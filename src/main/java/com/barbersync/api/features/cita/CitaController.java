package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import com.barbersync.api.features.cita.services.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public CitaResponse crear(@RequestBody @Valid CitaRequest request) {
        return citaService.crear(request);
    }

    @GetMapping("/{id}")
    public CitaResponse obtenerPorId(@PathVariable Integer id) {
        return citaService.obtenerPorId(id);
    }

    @GetMapping
    public List<CitaResponse> obtenerTodas() {
        return citaService.obtenerTodas();
    }

    @PutMapping("/{id}")
    public CitaResponse actualizar(@PathVariable Integer id, @RequestBody @Valid CitaRequest request) {
        return citaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        citaService.eliminar(id);
    }
}
