package com.barbersync.api.features.horario.services.impl;

import com.barbersync.api.features.horario.dto.HorarioRequest;
import com.barbersync.api.features.horario.dto.HorarioResponse;
import com.barbersync.api.features.horario.entities.TiempoSesion;
import com.barbersync.api.features.horario.Horario;
import com.barbersync.api.features.horario.HorarioRepository;
import com.barbersync.api.features.horario.HorarioMapper;
import com.barbersync.api.features.horario.services.HorarioService;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import com.barbersync.api.features.horario.TiempoSesionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TiempoSesionRepository tiempoSesionRepository;

    @Override
    public HorarioResponse crear(HorarioRequest request) {
        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));

        TiempoSesion tiempoSesion = tiempoSesionRepository.findById(request.getIdTiempoSesion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tiempo de sesión no encontrado"));

        Horario horario = new Horario();
        horario.setBarbero(barbero);
        horario.setHoraEntrada(request.getHoraEntrada());
        horario.setHoraSalida(request.getHoraSalida());
        horario.setTiempoSesion(tiempoSesion);

        horarioRepository.save(horario);
        return HorarioMapper.toResponse(horario);
    }

    @Override
    public HorarioResponse obtenerPorId(Integer id) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Horario no encontrado"));
        return HorarioMapper.toResponse(horario);
    }

    @Override
    public List<HorarioResponse> obtenerTodos() {
        return horarioRepository.findAll().stream()
                .map(HorarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HorarioResponse actualizar(Integer id, HorarioRequest request) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Horario no encontrado"));

        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));

        TiempoSesion tiempoSesion = tiempoSesionRepository.findById(request.getIdTiempoSesion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tiempo de sesión no encontrado"));

        horario.setBarbero(barbero);
        horario.setHoraEntrada(request.getHoraEntrada());
        horario.setHoraSalida(request.getHoraSalida());
        horario.setTiempoSesion(tiempoSesion);

        horarioRepository.save(horario);
        return HorarioMapper.toResponse(horario);
    }

    @Override
    public void eliminar(Integer id) {
        if (!horarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Horario no encontrado");
        }
        horarioRepository.deleteById(id);
    }
}
