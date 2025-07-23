package com.barbersync.api.features.usuario;

import com.barbersync.api.features.rol.Rol;
import com.barbersync.api.features.rol.RolRepository;
import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioMapper {
    private final RolRepository rolRepository;

    public UsuarioMapper(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(request.getPrimerNombre());
        usuario.setSegundoNombre(request.getSegundoNombre());
        usuario.setPrimerApellido(request.getPrimerApellido());
        usuario.setSegundoApellido(request.getSegundoApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena()); // Hash se aplicará en el servicio
        usuario.setFechaRegistro(LocalDate.now());

        // La línea usuario.setRolId() se ha eliminado.

        // Lógica perfecta para asignar el rol
        if (request.getRolId() != null) {
            Rol rol = rolRepository.findById(request.getRolId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + request.getRolId()));
            usuario.setRol(rol);
        }
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setPrimerNombre(usuario.getPrimerNombre());
        response.setSegundoNombre(usuario.getSegundoNombre());
        response.setPrimerApellido(usuario.getPrimerApellido());
        response.setSegundoApellido(usuario.getSegundoApellido());
        response.setCorreo(usuario.getCorreo());
        response.setFechaRegistro(usuario.getFechaRegistro().toString());
        response.setRolId(usuario.getRolId());
        // 👇 AÑADIR ESTA LÍNEA ESENCIAL 👇
        response.setUrlImagen(usuario.getUrlImagen());
        return response;
    }
    public void updateEntityFromRequest(Usuario usuario, UsuarioRequest request) {
        usuario.setPrimerNombre(request.getPrimerNombre());
        usuario.setSegundoNombre(request.getSegundoNombre());
        usuario.setPrimerApellido(request.getPrimerApellido());
        usuario.setSegundoApellido(request.getSegundoApellido());
        usuario.setCorreo(request.getCorreo());
        //usuario.setRolId(request.getRolId());
        // ⚠️ Contraseña se controla aparte desde el service para aplicar el hash solo si viene
    }
}
