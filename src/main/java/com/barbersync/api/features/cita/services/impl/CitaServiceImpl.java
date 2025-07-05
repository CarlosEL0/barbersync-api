package com.barbersync.api.features.cita.services.impl;

import com.barbersync.api.features.cita.Cita;
import com.barbersync.api.features.cita.CitaMapper;
import com.barbersync.api.features.cita.CitaRepository;
import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import com.barbersync.api.features.cita.entities.EstadoCita;
import com.barbersync.api.features.cita.services.CitaService;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;

    private final List<EstadoCita> estados = List.of(
            new EstadoCita(1, "Pendiente"),
            new EstadoCita(2, "Confirmada"),
            new EstadoCita(3, "Cancelada")
    );

    @Override
    public CitaResponse crear(CitaRequest request) {
        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));
        EstadoCita estado = estados.stream()
                .filter(e -> e.getId().equals(request.getEstadoCitaId()))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de cita inválido"));

        Cita cita = CitaMapper.toEntity(request);
        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setEstadoCita(estado);
        cita.setDuracionTotalMinutos(0); // Se puede calcular luego

        cita = citaRepository.save(cita);
        return CitaMapper.toResponse(cita);
    }

    @Override
    public CitaResponse obtenerPorId(Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada"));
        return CitaMapper.toResponse(cita);
    }

    @Override
    public List<CitaResponse> obtenerTodas() {
        return citaRepository.findAll().stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CitaResponse actualizar(Integer id, CitaRequest request) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada"));

        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));
        EstadoCita estado = estados.stream()
                .filter(e -> e.getId().equals(request.getEstadoCitaId()))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de cita inválido"));

        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setEstadoCita(estado);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());

        cita = citaRepository.save(cita);
        return CitaMapper.toResponse(cita);
    }

    @Override
    public void eliminar(Integer id) {
        citaRepository.deleteById(id);
    }
}
