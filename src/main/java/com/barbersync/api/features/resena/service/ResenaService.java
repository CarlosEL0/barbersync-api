package com.barbersync.api.features.resena.service;

import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;

import java.util.List;

public interface ResenaService {
    ResenaResponse crear(ResenaRequest request);
    ResenaResponse obtenerPorId(Integer id);
    List<ResenaResponse> obtenerTodas();
    void eliminar(Integer id);
}
