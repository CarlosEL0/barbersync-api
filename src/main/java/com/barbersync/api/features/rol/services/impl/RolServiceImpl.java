package com.barbersync.api.features.rol.services.impl;

import com.barbersync.api.features.rol.Rol;
import com.barbersync.api.features.rol.RolRepository;
import com.barbersync.api.features.rol.dto.RolRequest;
import com.barbersync.api.features.rol.dto.RolResponse;
import com.barbersync.api.features.rol.services.RolService;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public RolResponse crearRol(RolRequest request) {
        Rol rol = new Rol();
        rol.setNombre(request.getNombre());
        rol = rolRepository.save(rol);
        return mapToResponse(rol);
    }

    @Override
    public RolResponse obtenerPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + id));
        return mapToResponse(rol);
    }

    @Override
    public List<RolResponse> obtenerTodos() {
        return rolRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RolResponse actualizarRol(Integer id, RolRequest request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + id));

        rol.setNombre(request.getNombre());
        rol = rolRepository.save(rol);
        return mapToResponse(rol);
    }

    @Override
    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }

    private RolResponse mapToResponse(Rol rol) {
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        return response;
    }
}
