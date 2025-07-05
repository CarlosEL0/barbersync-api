package com.barbersync.api.features.servicio;

import com.barbersync.api.features.servicio.dto.ServicioRequest;
import com.barbersync.api.features.servicio.dto.ServicioResponse;
import com.barbersync.api.features.servicio.services.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ServicioController {

    private final ServicioService servicioService;

    @PostMapping
    public ServicioResponse crear(@RequestBody @Valid ServicioRequest request) {
        return servicioService.crear(request);
    }

    @GetMapping("/{id}")
    public ServicioResponse obtenerPorId(@PathVariable Integer id) {
        return servicioService.obtenerPorId(id);
    }

    @GetMapping
    public List<ServicioResponse> obtenerTodos() {
        return servicioService.obtenerTodos();
    }

    @PutMapping("/{id}")
    public ServicioResponse actualizar(@PathVariable Integer id, @RequestBody @Valid ServicioRequest request) {
        return servicioService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        servicioService.eliminar(id);
    }
}
