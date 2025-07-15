package com.barbersync.api.features.resena.service;

import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;

import java.time.LocalDate;
import java.util.List;

public interface ResenaService {
    ResenaResponse crear(ResenaRequest request);
    ResenaResponse obtenerPorId(Integer id);
    List<ResenaResponse> obtenerTodas();
    ResenaResponse actualizar(Integer id, ResenaRequest request);
    void eliminar(Integer id);

    // 🔥 Nuevos métodos agregados:
    List<ResenaResponse> obtenerPorBarbero(Integer idBarbero);
    List<ResenaResponse> obtenerPorCliente(Integer idCliente);
    Double obtenerPromedioCalificacion(Integer idBarbero);
    List<ResenaResponse> obtenerPorBarberoEntreFechas(Integer idBarbero, LocalDate inicio, LocalDate fin);
}
