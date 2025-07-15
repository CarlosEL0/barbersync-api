package com.barbersync.api.features.cita.services.impl;

// (Tus imports se mantienen igual, solo asegúrate de tener el de Transactional)
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
import com.barbersync.api.features.cita.EstadoCitaRepository;
import org.springframework.transaction.annotation.Transactional; // <-- Importante
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final ServicioRepository servicioRepository;
    private final CitaServicioRepository citaServicioRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoCitaRepository estadoCitaRepository;

    @Override
    @Transactional // Los métodos de escritura ya están bien con @Transactional
    public CitaResponse crear(CitaRequest request) {
        // ... (Tu código aquí se mantiene igual)
        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());

        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));
        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        cita.setBarbero(barbero);
        cita.setCliente(cliente);

        EstadoCita estado = estadoCitaRepository.findById(request.getEstadoCitaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de cita inválido"));
        cita.setEstadoCita(estado);

        List<Servicio> servicios = servicioRepository.findAllById(request.getIdServicios());
        int duracionTotal = servicios.stream()
                .mapToInt(Servicio::getDuracionMinuto)
                .sum();
        cita.setDuracionTotalMinutos(duracionTotal);

        Cita guardada = citaRepository.save(cita);

        for (Servicio servicio : servicios) {
            CitaServicioId idServicio = new CitaServicioId();
            idServicio.setIdCita(guardada.getId());
            idServicio.setIdServicio(servicio.getId());
            CitaServicio citaServicio = new CitaServicio();
            citaServicio.setId(idServicio);
            citaServicio.setCita(guardada);
            citaServicio.setServicio(servicio);
            citaServicioRepository.save(citaServicio);
        }

        return CitaMapper.toResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA
    public CitaResponse obtenerPorId(Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada"));
        return CitaMapper.toResponse(cita);
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA
    public List<CitaResponse> obtenerTodas() {
        return citaRepository.findAll().stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitaResponse actualizar(Integer id, CitaRequest request) {
        // ... (Tu código aquí se mantiene igual)
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada"));
        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));
        EstadoCita estado = estadoCitaRepository.findById(request.getEstadoCitaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estado de cita inválido"));
        cita.setCliente(cliente);
        cita.setBarbero(barbero);
        cita.setEstadoCita(estado);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        List<Servicio> servicios = servicioRepository.findAllById(request.getIdServicios());
        int duracionTotal = servicios.stream().mapToInt(Servicio::getDuracionMinuto).sum();
        cita.setDuracionTotalMinutos(duracionTotal);
        cita = citaRepository.save(cita);
        citaServicioRepository.deleteAllByCita_Id(id);
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
    @Transactional // <-- ANOTACIÓN AÑADIDA para consistencia
    public void eliminar(Integer id) {
        if (!citaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Cita no encontrada para eliminar");
        }
        citaServicioRepository.deleteAllByCita_Id(id);
        citaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA
    public List<CitaResponse> obtenerPorCliente(Integer idCliente) {
        return citaRepository.findByCliente_Id(idCliente).stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA
    public List<CitaResponse> obtenerPorBarbero(Integer idBarbero) {
        return citaRepository.findByBarbero_Id(idBarbero).stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA
    public List<CitaResponse> obtenerPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha).stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA (LA MÁS IMPORTANTE)
    public List<CitaResponse> obtenerPorBarberoYFecha(Integer idBarbero, LocalDate fecha) {
        return citaRepository.findByBarbero_IdAndFecha(idBarbero, fecha).stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true) // <-- ANOTACIÓN AÑADIDA
    public List<CitaResponse> obtenerPorClienteYEstado(Integer idCliente, String estado) {
        return citaRepository.findByCliente_IdAndEstadoCita_NombreEstado(idCliente, estado).stream()
                .map(CitaMapper::toResponse)
                .collect(Collectors.toList());
    }
}