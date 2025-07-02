package com.barbersync.api.features.rol;

import com.barbersync.api.features.rol.dto.RolRequest;
import com.barbersync.api.features.rol.dto.RolResponse;
import com.barbersync.api.features.rol.services.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping
    public RolResponse crearRol(@RequestBody RolRequest request) {
        return rolService.crearRol(request);
    }

    @GetMapping("/{id}")
    public RolResponse obtenerPorId(@PathVariable Integer id) {
        return rolService.obtenerPorId(id);
    }

    @GetMapping
    public List<RolResponse> obtenerTodos() {
        return rolService.obtenerTodos();
    }

    @PutMapping("/{id}")
    public RolResponse actualizarRol(@PathVariable Integer id, @RequestBody RolRequest request) {
        return rolService.actualizarRol(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
    }
}
