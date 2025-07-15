package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.dto.*;
import com.barbersync.api.features.barbero.services.IBarberoEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barberos/especialidades")
@RequiredArgsConstructor
public class BarberoEspecialidadController {
    private final IBarberoEspecialidadService service;

    // REGLA: Un BARBERO o un ADMIN pueden asignar especialidades.
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #request.idUsuario == authentication.principal.id)")
    public ResponseEntity<Void> asignarEspecialidades(@RequestBody BarberoEspecialidadRequest request) {
        service.asignarEspecialidades(request);
        return ResponseEntity.ok().build();
    }

    // REGLA: CUALQUIERA logueado puede ver las especialidades de un barbero.
    @GetMapping("/{idUsuario}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BarberoEspecialidadResponse> obtener(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.obtenerPorUsuario(idUsuario));
    }

    // REGLA: Un BARBERO o un ADMIN pueden eliminar.
    @DeleteMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #idUsuario == authentication.principal.id)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idUsuario) {
        service.eliminarEspecialidadesDeBarbero(idUsuario);
        return ResponseEntity.noContent().build();
    }

    // REGLA: Un BARBERO o un ADMIN pueden actualizar.
    @PutMapping("/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #idUsuario == authentication.principal.id)")
    public ResponseEntity<Void> actualizarEspecialidades(@PathVariable Integer idUsuario, @RequestBody BarberoEspecialidadRequest request) {
        //...
        return ResponseEntity.ok().build();
    }
}
