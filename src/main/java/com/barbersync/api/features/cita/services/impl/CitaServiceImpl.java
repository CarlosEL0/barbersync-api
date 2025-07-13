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
import com.barbersync.api.features.servicio.Servicio;
import com.barbersync.api.features.servicio.ServicioRepository;
import com.barbersync.api.features.cita.entities.CitaServicio;
import com.barbersync.api.features.cita.entities.CitaServicioId;
import com.barbersync.api.features.cita.CitaServicioRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {
    private final ServicioRepository servicioRepository;
    private final CitaServicioRepository citaServicioRepository;
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

        // Crear cita base
        Cita cita = CitaMapper.toEntity(request);
        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setEstadoCita(estado);
        cita.setDuracionTotalMinutos(0); // inicial

        // Guardar para obtener ID
        cita = citaRepository.save(cita);

        // Obtener servicios por ID
        var servicios = servicioRepository.findAllById(request.getIdServicios());

        // Calcular duración y registrar cita_servicio
        int duracionTotal = 0;
        for (Servicio servicio : servicios) {
            CitaServicioId id = new CitaServicioId();
            id.setIdCita(cita.getId());
            id.setIdServicio(servicio.getId());

            CitaServicio cs = new CitaServicio();
            cs.setId(id);
            cs.setCita(cita);
            cs.setServicio(servicio);

            citaServicioRepository.save(cs);

            duracionTotal += servicio.getDuracionMinuto(); // campo real de la entidad
        }

        // Actualizar duración total
        cita.setDuracionTotalMinutos(duracionTotal);
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
    @Transactional
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

        // Recalcular duración total de la cita
        List<Servicio> servicios = servicioRepository.findAllById(request.getIdServicios());
        int duracionTotal = servicios.stream()
                .mapToInt(Servicio::getDuracionMinuto)
                .sum();

        // Actualizar la duración total
        cita.setDuracionTotalMinutos(duracionTotal);
        cita = citaRepository.save(cita);

        // Eliminar los servicios asociados a la cita
        citaServicioRepository.deleteAllByCita_Id(id);

        // Guardar los nuevos servicios
        for (Servicio servicio : servicios) {
            CitaServicioId idServicio = new CitaServicioId();
            idServicio.setIdCita(cita.getId());
            idServicio.setIdServicio(servicio.getId());

            CitaServicio citaServicio = new CitaServicio();
            citaServicio.setId(idServicio);
            citaServicio.setCita(cita);
            citaServicio.setServicio(servicio);

            citaServicioRepository.save(citaServicio);
        }

        return CitaMapper.toResponse(cita);
    }




    @Override
    public void eliminar(Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada"));

        // Eliminar los servicios asociados a la cita
        citaServicioRepository.deleteAllByCita_Id(id);

        // Eliminar la cita
        citaRepository.deleteById(id);
    }
    @Override
    public List<CitaResponse> obtenerPorCliente(Integer idCliente) {
        List<Cita> citas = citaRepository.findByCliente_Id(idCliente);
        return citas.stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

}

