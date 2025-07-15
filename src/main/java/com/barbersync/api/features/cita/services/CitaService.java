package com.barbersync.api.features.cita.services;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;

import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    CitaResponse crear(CitaRequest request);
    CitaResponse obtenerPorId(Integer id);
    List<CitaResponse> obtenerTodas();
    CitaResponse actualizar(Integer id, CitaRequest request);
    void eliminar(Integer id);
    List<CitaResponse> obtenerPorCliente(Integer idCliente);
    List<CitaResponse> obtenerPorBarbero(Integer idBarbero);
    List<CitaResponse> obtenerPorFecha(LocalDate fecha);
    List<CitaResponse> obtenerPorBarberoYFecha(Integer idBarbero, LocalDate fecha);
    List<CitaResponse> obtenerPorClienteYEstado(Integer idCliente, String estado);

}
