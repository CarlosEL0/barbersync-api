package com.barbersync.api.features.rol;

import com.barbersync.api.features.rol.dto.RolRequest;
import com.barbersync.api.features.rol.dto.RolResponse;
import com.barbersync.api.features.rol.services.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RolResponse crearRol(@RequestBody @Valid RolRequest request) {
        return rolService.crearRol(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RolResponse obtenerPorId(@PathVariable Integer id) {
        return rolService.obtenerPorId(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<RolResponse> obtenerTodos() {
        return rolService.obtenerTodos();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RolResponse actualizarRol(@PathVariable Integer id, @RequestBody @Valid RolRequest request) {
        return rolService.actualizarRol(id, request);
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
    }
}