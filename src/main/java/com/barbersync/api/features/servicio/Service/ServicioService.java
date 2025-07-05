package com.barbersync.api.features.servicio.services;

import com.barbersync.api.features.servicio.dto.ServicioRequest;
import com.barbersync.api.features.servicio.dto.ServicioResponse;

import java.util.List;

public interface ServicioService {
    ServicioResponse crear(ServicioRequest request);
    ServicioResponse obtenerPorId(Integer id);
    List<ServicioResponse> obtenerTodos();
    ServicioResponse actualizar(Integer id, ServicioRequest request);
    void eliminar(Integer id);
}
