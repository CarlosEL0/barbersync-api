package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.dto.HorarioRequest;
import com.barbersync.api.features.horario.dto.HorarioResponse;
import com.barbersync.api.features.horario.services.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioController {
    private final HorarioService horarioService;

    // REGLA: Cualquiera logueado puede ver los horarios (para saber cuándo agendar).
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HorarioResponse>> obtenerTodos() {
        return ResponseEntity.ok(horarioService.obtenerTodos());
    }

    // REGLA: Cualquiera logueado puede ver un horario específico.
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HorarioResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(horarioService.obtenerPorId(id));
    }

    // REGLA: Un BARBERO crea su propio horario. Un ADMIN para cualquiera.
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BARBERO')")
    public ResponseEntity<HorarioResponse> crear(@RequestBody HorarioRequest request) {
        // AÑADIR VALIDACIÓN EN EL SERVICIO para que un barbero no cree para otro
        return ResponseEntity.ok(horarioService.crear(request));
    }

    // REGLA: Un BARBERO actualiza su propio horario. Un ADMIN cualquiera.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @horarioSecurityService.esPropietario(authentication, #id)")
    public ResponseEntity<HorarioResponse> actualizar(@PathVariable Integer id, @RequestBody HorarioRequest request) {
        return ResponseEntity.ok(horarioService.actualizar(id, request));
    }

    // REGLA: Un BARBERO elimina su propio horario. Un ADMIN cualquiera.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @horarioSecurityService.esPropietario(authentication, #id)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
