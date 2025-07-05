package com.barbersync.api.features.cita.services;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;

import java.util.List;

public interface CitaService {

    CitaResponse crear(CitaRequest request);

    CitaResponse obtenerPorId(Integer id);

    List<CitaResponse> obtenerTodas();

    CitaResponse actualizar(Integer id, CitaRequest request);

    void eliminar(Integer id);
}
