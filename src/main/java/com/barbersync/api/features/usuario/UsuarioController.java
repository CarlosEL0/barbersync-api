package com.barbersync.api.features.usuario;

import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Svelte/Vite
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioResponse crearUsuario(@RequestBody @Valid UsuarioRequest request) {
        return usuarioService.crearUsuario(request);
    }

    @GetMapping("/{id}")
    public UsuarioResponse obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @GetMapping
    public List<UsuarioResponse> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizarUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioRequest request) {
        return usuarioService.actualizarUsuario(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }
}
