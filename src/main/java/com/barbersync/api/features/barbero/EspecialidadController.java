package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.entities.Especialidad;
import com.barbersync.api.features.barbero.repositories.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadRepository especialidadRepository;

    // CUALQUIERA logueado puede ver la lista de especialidades
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Especialidad> obtenerTodas() {
        return especialidadRepository.findAll(); // ✅ Ya no es static
    }

    // Solo un ADMIN puede crear especialidades
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Especialidad crearEspecialidad(@RequestBody Especialidad especialidad) {
        return especialidadRepository.save(especialidad); // ✅ ejemplo completo
    }

    // Solo un ADMIN puede eliminar especialidades
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarEspecialidad(@PathVariable Integer id) {
        especialidadRepository.deleteById(id); // ✅ ejemplo completo
    }
}
