package com.barbersync.api.features.usuario;

import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(request.getPrimerNombre());
        usuario.setSegundoNombre(request.getSegundoNombre());
        usuario.setPrimerApellido(request.getPrimerApellido());
        usuario.setSegundoApellido(request.getSegundoApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(request.getContrasena()); // ⚠️ será reemplazada por hash en el service
        usuario.setRolId(request.getRolId());
        usuario.setFechaRegistro(LocalDate.now());
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
