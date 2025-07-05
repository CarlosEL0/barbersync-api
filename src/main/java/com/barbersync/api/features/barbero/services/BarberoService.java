package com.barbersync.api.features.barbero.services;

import com.barbersync.api.features.barbero.dto.BarberoRequestDto;
import com.barbersync.api.features.barbero.dto.BarberoResponseDto;

import java.util.List;

public interface BarberoService {
    BarberoResponseDto crear(BarberoRequestDto request);
    BarberoResponseDto obtenerPorId(Integer id);
    List<BarberoResponseDto> obtenerTodos();
    BarberoResponseDto actualizar(Integer id, BarberoRequestDto request);
    void eliminar(Integer id);
}
