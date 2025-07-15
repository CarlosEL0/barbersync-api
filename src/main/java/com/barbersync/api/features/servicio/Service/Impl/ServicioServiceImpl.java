package com.barbersync.api.features.servicio.Service.Impl;

import com.barbersync.api.features.servicio.*;
import com.barbersync.api.features.servicio.dto.ServicioRequest;
import com.barbersync.api.features.servicio.dto.ServicioResponse;
import com.barbersync.api.features.servicio.Service.ServicioService;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;
    private final ServicioMapper servicioMapper;

    @Override
    public ServicioResponse crear(ServicioRequest request) {
        Servicio servicio = servicioMapper.toEntity(request);
        servicio = servicioRepository.save(servicio);
        return servicioMapper.toResponse(servicio);
    }

    @Override
    public ServicioResponse obtenerPorId(Integer id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con ID: " + id));
        return servicioMapper.toResponse(servicio);
    }

    @Override
    public List<ServicioResponse> obtenerTodos() {
        return servicioRepository.findAll().stream()
                .map(servicioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServicioResponse actualizar(Integer id, ServicioRequest request) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con ID: " + id));
        servicioMapper.updateEntityFromRequest(servicio, request);
        servicio = servicioRepository.save(servicio);
        return servicioMapper.toResponse(servicio);
    }

    @Override
    public void eliminar(Integer id) {
        servicioRepository.deleteById(id);
    }
}
