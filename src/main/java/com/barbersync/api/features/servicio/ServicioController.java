package com.barbersync.api.features.servicio;

import com.barbersync.api.features.servicio.dto.ServicioRequest;
import com.barbersync.api.features.servicio.dto.ServicioResponse;
import com.barbersync.api.features.servicio.Service.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ServicioResponse crear(@RequestBody @Valid ServicioRequest request) {
        return servicioService.crear(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ServicioResponse obtenerPorId(@PathVariable Integer id) {
        return servicioService.obtenerPorId(id);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ServicioResponse> obtenerTodos() {
        return servicioService.obtenerTodos();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ServicioResponse actualizar(@PathVariable Integer id, @RequestBody @Valid ServicioRequest request) {
        return servicioService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar (@PathVariable Integer id) {
        servicioService.eliminar(id);
    }
}