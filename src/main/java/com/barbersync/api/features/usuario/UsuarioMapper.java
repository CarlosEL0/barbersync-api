package com.barbersync.api.features.usuario;

import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.rol.Rol;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest dto, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(dto.getPrimerNombre());
        usuario.setSegundoNombre(dto.getSegundoNombre());
        usuario.setPrimerApellido(dto.getPrimerApellido());
        usuario.setSegundoApellido(dto.getSegundoApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(dto.getContrasena());
        usuario.setFechaRegistro(java.time.LocalDate.now()); // o lo que uses
        usuario.setRol(rol);
        return usuario;
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(entity.getId());
        response.setPrimerNombre(entity.getPrimerNombre());
        response.setSegundoNombre(entity.getSegundoNombre());
        response.setPrimerApellido(entity.getPrimerApellido());
        response.setSegundoApellido(entity.getSegundoApellido());
        response.setCorreo(entity.getCorreo());
        response.setFechaRegistro(entity.getFechaRegistro());

        // ✅ Incluimos ID y nombre del rol
        if (entity.getRol() != null) {
            response.setRolId(entity.getRol().getId());
            response.setRolNombre(entity.getRol().getRol().toLowerCase()); // en minúscula
        }

        return response;
    }
}
