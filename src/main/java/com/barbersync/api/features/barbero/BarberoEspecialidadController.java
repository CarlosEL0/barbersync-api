package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.dto.BarberoEspecialidadRequest;
import com.barbersync.api.features.barbero.dto.BarberoEspecialidadResponse;
import com.barbersync.api.features.barbero.services.IBarberoEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barberos") // ✅ Ruta base unificada
@RequiredArgsConstructor
public class BarberoEspecialidadController {

    private final IBarberoEspecialidadService service;

    // ✅ Asignar especialidades a un barbero (POST)
    @PostMapping("/{idUsuario}/especialidades")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #idUsuario == authentication.principal.id)")
    public ResponseEntity<Void> asignarEspecialidades(
            @PathVariable Integer idUsuario,
            @RequestBody List<Integer> idEspecialidades) {

        BarberoEspecialidadRequest request = new BarberoEspecialidadRequest(idUsuario, idEspecialidades);
        service.asignarEspecialidades(request);
        return ResponseEntity.ok().build();
    }

    // ✅ Obtener especialidades de un barbero (GET)
    @GetMapping("/{idUsuario}/especialidades")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BarberoEspecialidadResponse> obtener(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.obtenerPorUsuario(idUsuario));
    }

    // ✅ Eliminar todas las especialidades de un barbero (DELETE)
    @DeleteMapping("/{idUsuario}/especialidades")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #idUsuario == authentication.principal.id)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idUsuario) {
        service.eliminarEspecialidadesDeBarbero(idUsuario);
        return ResponseEntity.noContent().build();
    }

    // ✅ Actualizar todas las especialidades (PUT)
    @PutMapping("/{idUsuario}/especialidades")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #idUsuario == authentication.principal.id)")
    public ResponseEntity<Void> actualizarEspecialidades(
            @PathVariable Integer idUsuario,
            @RequestBody List<Integer> idEspecialidades) {

        BarberoEspecialidadRequest request = new BarberoEspecialidadRequest(idUsuario, idEspecialidades);
        service.asignarEspecialidades(request); // Se usa el mismo método porque sobreescribe
        return ResponseEntity.ok().build();
    }
}
