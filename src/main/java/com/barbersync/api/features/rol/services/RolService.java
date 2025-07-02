package com.barbersync.api.features.rol.services;

import com.barbersync.api.features.rol.dto.RolRequest;
import com.barbersync.api.features.rol.dto.RolResponse;

import java.util.List;

public interface RolService {
    RolResponse crearRol(RolRequest request);
    RolResponse obtenerPorId(Integer id);
    List<RolResponse> obtenerTodos();
    RolResponse actualizarRol(Integer id, RolRequest request);
    void eliminarRol(Integer id);
}
