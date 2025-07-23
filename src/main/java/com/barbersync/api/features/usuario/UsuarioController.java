package com.barbersync.api.features.usuario;

import com.barbersync.api.cloudinary.services.CloudinaryService;
import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.usuario.dto.UsuarioUpdateRequest;
import com.barbersync.api.features.usuario.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final CloudinaryService cloudinaryService;

    // REGLA: Solo un ADMIN crea usuarios desde aquí.
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponse crearUsuario(@RequestBody @Valid UsuarioRequest request) {
        return usuarioService.crearUsuario(request);
    }

    // REGLA: Cualquiera logueado puede ver un perfil (para ver detalles de un barbero).
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UsuarioResponse obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // REGLA: ADMIN ve a todos. Los demás, solo a los BARBEROS.
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<UsuarioResponse> obtenerUsuarios(@RequestParam(required = false) String rol) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean esAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if ("BARBERO".equalsIgnoreCase(rol)) {
            return usuarioService.obtenerUsuariosPorRol("BARBERO");
        }

        if (esAdmin) {
            return usuarioService.obtenerUsuarios();
        } else {
            return usuarioService.obtenerUsuariosPorRol("BARBERO");
        }
    }

    // REGLA: Un usuario actualiza su propio perfil. Un ADMIN actualiza cualquiera.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
//           👇👇👇 CAMBIO AQUÍ 👇👇👇
    public UsuarioResponse actualizarUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioUpdateRequest request) {
        //                                  👆👆👆 Y AQUÍ 👆👆👆
        return usuarioService.actualizarUsuario(id, request);
    }

    // REGLA: Solo un ADMIN elimina usuarios.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }

    // NUEVO: Subir imagen de perfil a Cloudinary
    @PostMapping("/{id}/imagen")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<?> subirImagenDePerfil(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        String urlImagen = cloudinaryService.uploadFile(file);
        UsuarioResponse usuarioActualizado = usuarioService.actualizarUrlImagen(id, urlImagen);
        return ResponseEntity.ok(usuarioActualizado);
    }
}
