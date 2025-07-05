package com.barbersync.api.features.horario.services;

import com.barbersync.api.features.horario.dto.HorarioRequest;
import com.barbersync.api.features.horario.dto.HorarioResponse;

import java.util.List;

public interface HorarioService {
    HorarioResponse crear(HorarioRequest request);
    HorarioResponse obtenerPorId(Integer id);
    List<HorarioResponse> obtenerTodos();
    HorarioResponse actualizar(Integer id, HorarioRequest request);
    void eliminar(Integer id);
}
