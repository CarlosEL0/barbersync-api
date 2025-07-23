    package com.barbersync.api.features.cita.services.impl;

    import com.barbersync.api.features.cita.*;
    import com.barbersync.api.features.cita.dto.CitaRequest;
    import com.barbersync.api.features.cita.dto.CitaResponse;
    import com.barbersync.api.features.cita.entities.CitaServicio;
    import com.barbersync.api.features.cita.entities.CitaServicioId;
    import com.barbersync.api.features.cita.entities.EstadoCita;
    import com.barbersync.api.features.cita.services.CitaService;
    import com.barbersync.api.features.servicio.Servicio;
    import com.barbersync.api.features.servicio.ServicioRepository;
    import com.barbersync.api.features.usuario.Usuario;
    import com.barbersync.api.features.usuario.UsuarioRepository;
    import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
    import jakarta.persistence.EntityNotFoundException;
    import lombok.RequiredArgsConstructor;
    import org.slf4j.Logger; // <-- IMPORTANTE
    import org.slf4j.LoggerFactory; // <-- IMPORTANTE
    import org.springframework.scheduling.annotation.Scheduled;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class CitaServiceImpl implements CitaService {

        private static final Logger log = LoggerFactory.getLogger(CitaServiceImpl.class); // ✅ LOGGER

        private final ServicioRepository servicioRepository;
        private final CitaServicioRepository citaServicioRepository;
        private final CitaRepository citaRepository;
        private final UsuarioRepository usuarioRepository;
        private final EstadoCitaRepository estadoCitaRepository;

        @Override
        @Transactional
        public CitaResponse crear(CitaRequest request) {
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
            int duracionTotal = servicios.stream().mapToInt(Servicio::getDuracionMinuto).sum();
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
        @Transactional(readOnly = true)
        public CitaResponse obtenerPorId(Integer id) {
            Cita cita = citaRepository.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada"));
            return CitaMapper.toResponse(cita);
        }

        @Override
        @Transactional(readOnly = true)
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
        @Transactional
        public void eliminar(Integer id) {
            if (!citaRepository.existsById(id)) {
                throw new RecursoNoEncontradoException("Cita no encontrada para eliminar");
            }
            citaServicioRepository.deleteAllByCita_Id(id);
            citaRepository.deleteById(id);
        }

        @Override
        @Transactional(readOnly = true)
        public List<CitaResponse> obtenerPorCliente(Integer idCliente) {
            return citaRepository.findByCliente_Id(idCliente).stream()
                    .map(CitaMapper::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<CitaResponse> obtenerPorBarbero(Integer idBarbero) {
            return citaRepository.findByBarbero_Id(idBarbero).stream()
                    .map(CitaMapper::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<CitaResponse> obtenerPorFecha(LocalDate fecha) {
            return citaRepository.findByFecha(fecha).stream()
                    .map(CitaMapper::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<CitaResponse> obtenerPorBarberoYFecha(Integer idBarbero, LocalDate fecha) {
            return citaRepository.findByBarbero_IdAndFecha(idBarbero, fecha).stream()
                    .map(CitaMapper::toResponse)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<CitaResponse> obtenerPorClienteYEstado(Integer idCliente, String estado) {
            return citaRepository.findByCliente_IdAndEstadoCita_NombreEstado(idCliente, estado).stream()
                    .map(CitaMapper::toResponse)
                    .collect(Collectors.toList());
        }

        // ✅ METODO PROGRAMADO CON LOGS PARA DEPURACIÓN
        @Override
        @Transactional
        @Scheduled(cron = "0 * * * * ?") // Cada minuto para pruebas
        public void actualizarEstadoCitasARealizada() {
            log.info("⏰ [TAREA PROGRAMADA] Iniciando verificación de citas pasadas...");

            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now();

            List<Cita> citasParaActualizar = citaRepository.findCitasPasadasParaActualizar(fechaActual, horaActual);

            log.info("🔍 [TAREA PROGRAMADA] Se encontraron {} citas para actualizar.", citasParaActualizar.size());

            if (citasParaActualizar.isEmpty()) {
                log.info("✅ [TAREA PROGRAMADA] No hay citas que actualizar. Tarea finalizada.");
                return;
            }

            EstadoCita estadoRealizada = estadoCitaRepository.findByNombreEstado("Realizada")
                    .orElseThrow(() -> new IllegalStateException("El estado 'Realizada' no fue encontrado en la base de datos."));

            for (Cita cita : citasParaActualizar) {
                cita.setEstadoCita(estadoRealizada);
            }

            citaRepository.saveAll(citasParaActualizar);

            log.info("💾 [TAREA PROGRAMADA] ¡ÉXITO! Se actualizaron {} citas al estado 'Realizada'.", citasParaActualizar.size());
        }

        @Override
        public CitaResponse actualizarEstado(Integer idCita, String nuevoEstado) {
            // 1. Buscar la cita
            var cita = citaRepository.findById(idCita)
                    .orElseThrow(() -> new EntityNotFoundException("Cita no encontrada con id: " + idCita));

            // 2. Buscar el objeto EstadoCita
            var estado = estadoCitaRepository.findByNombreEstado(nuevoEstado)
                    .orElseThrow(() -> new IllegalArgumentException("El estado '" + nuevoEstado + "' no es válido."));

            // 3. Actualizar el estado en la entidad
            cita.setEstadoCita(estado); // <-- Primera corrección

            // 4. Guardar la cita
            var citaGuardada = citaRepository.save(cita);

            // 5. Mapear la entidad guardada a un DTO de respuesta y devolverla
            return CitaMapper.toResponse(citaGuardada); // <-- Segunda corrección
        }
    }
