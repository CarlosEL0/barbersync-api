package com.barbersync.api.features.usuario.services;

import java.util.List;
import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.usuario.dto.UsuarioUpdateRequest;

public interface UsuarioService {

    UsuarioResponse crearUsuario(UsuarioRequest usuarioRequest);
    UsuarioResponse obtenerUsuarioPorId(Integer id);
    List<UsuarioResponse> obtenerUsuarios();
    UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest usuarioRequest);
    void eliminarUsuario(Integer id);
    List<UsuarioResponse> obtenerUsuariosPorRol(String rol);

    // ✅✅✅ AÑADE ESTA LÍNEA AQUÍ ✅✅✅
    UsuarioResponse actualizarUrlImagen(Integer id, String urlImagen);
    // ✅✅✅ AÑADE ESTA SOBRECARGA DEL MÉTODO ✅✅✅
    UsuarioResponse actualizarUsuario(Integer id, UsuarioUpdateRequest usuarioUpdateRequest);}