package com.barbersync.api.features.barbero.services.impl;

import com.barbersync.api.features.barbero.*;
import com.barbersync.api.features.barbero.dto.BarberoRequestDto;
import com.barbersync.api.features.barbero.dto.BarberoResponseDto;
import com.barbersync.api.features.barbero.entities.*;
import com.barbersync.api.features.barbero.services.BarberoService;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarberoServiceImpl implements BarberoService {

    private final BarberoRepository barberoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadRepository especialidadRepository;
    private final BarberoMapper barberoMapper;

    @Override
    public BarberoResponseDto crear(BarberoRequestDto request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Barbero barbero = new Barbero();
        barbero.setUsuario(usuario);

        List<BarberoEspecialidad> especialidades = request.getEspecialidades().stream()
                .map(id -> {
                    Especialidad especialidad = especialidadRepository.findById(id)
                            .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada"));
                    BarberoEspecialidad be = new BarberoEspecialidad();
                    be.setEspecialidad(especialidad);
                    be.setBarbero(barbero);
                    return be;
                }).collect(Collectors.toList());

        barbero.setEspecialidades(especialidades);
        barberoRepository.save(barbero);
        return barberoMapper.toResponse(barbero);
    }

    @Override
    public BarberoResponseDto obtenerPorId(Integer id) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));
        return barberoMapper.toResponse(barbero);
    }

    @Override
    public List<BarberoResponseDto> obtenerTodos() {
        return barberoRepository.findAll().stream()
                .map(barberoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BarberoResponseDto actualizar(Integer id, BarberoRequestDto request) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        barbero.setUsuario(usuario);

        List<BarberoEspecialidad> especialidades = request.getEspecialidades().stream()
                .map(eid -> {
                    Especialidad especialidad = especialidadRepository.findById(eid)
                            .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada"));
                    BarberoEspecialidad be = new BarberoEspecialidad();
                    be.setEspecialidad(especialidad);
                    be.setBarbero(barbero);
                    return be;
                }).collect(Collectors.toList());

        barbero.getEspecialidades().clear();
        barbero.getEspecialidades().addAll(especialidades);

        barberoRepository.save(barbero);
        return barberoMapper.toResponse(barbero);
    }

    @Override
    public void eliminar(Integer id) {
        if (!barberoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Barbero no encontrado");
        }
        barberoRepository.deleteById(id);
    }
}
