package com.barbersync.api.features.barbero;

import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.usuario.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/barberos")
@RequiredArgsConstructor
public class BarberoController {

    private final UsuarioService usuarioService;

    /**
     * Endpoint dedicado exclusivamente a devolver una lista de todos los usuarios
     * que tienen el rol de "BARBERO".
     *
     * @return Una lista de DTOs de usuario.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()") // <-- AHORA permite a CUALQUIER usuario autenticado
    public List<UsuarioResponse> listarTodosLosBarberos() {
        return usuarioService.obtenerUsuariosPorRol("BARBERO");
    }
}