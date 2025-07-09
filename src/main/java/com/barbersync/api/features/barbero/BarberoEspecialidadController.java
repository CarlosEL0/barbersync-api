package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.dto.*;
import com.barbersync.api.features.barbero.services.IBarberoEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/barberos/especialidades")
@RequiredArgsConstructor
public class BarberoEspecialidadController {

    private final IBarberoEspecialidadService service;

    @PostMapping
    public ResponseEntity<Void> asignarEspecialidades(@RequestBody BarberoEspecialidadRequest request) {
        service.asignarEspecialidades(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<BarberoEspecialidadResponse> obtener(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.obtenerPorUsuario(idUsuario));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idUsuario) {
        service.eliminarEspecialidadesDeBarbero(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Void> actualizarEspecialidades(
            @PathVariable Integer idUsuario,
            @RequestBody BarberoEspecialidadRequest request) {
        System.out.println("==> PUT recibido para actualizar especialidades");
        request.setIdUsuario(idUsuario);
        service.eliminarEspecialidadesDeBarbero(idUsuario);
        service.asignarEspecialidades(request);
        System.out.println("==> PUT completado exitosamente");
        return ResponseEntity.ok().build();
    }

}
