package com.barbersync.api.features.disponibilidad.services.impl;

import com.barbersync.api.features.cita.Cita;
import com.barbersync.api.features.cita.CitaRepository;
import com.barbersync.api.features.disponibilidad.DisponibilidadService;
import com.barbersync.api.features.disponibilidad.dto.HorarioDisponibleDTO;
import com.barbersync.api.features.horario.Horario;
import com.barbersync.api.features.horario.HorarioRepository;
import com.barbersync.api.features.servicio.Servicio;
import com.barbersync.api.features.servicio.ServicioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DisponibilidadServiceImpl implements DisponibilidadService {

    private final HorarioRepository horarioRepository;
    private final CitaRepository citaRepository;
    private final ServicioRepository servicioRepository;

    // El mapa de días ya no es necesario para la lógica principal, pero lo dejamos por si acaso.
    private static final Map<DayOfWeek, String> MAPA_DIAS = Map.of(
            DayOfWeek.MONDAY, "LUNES", DayOfWeek.TUESDAY, "MARTES",
            DayOfWeek.WEDNESDAY, "MIERCOLES", DayOfWeek.THURSDAY, "JUEVES",
            DayOfWeek.FRIDAY, "VIERNES", DayOfWeek.SATURDAY, "SABADO",
            DayOfWeek.SUNDAY, "DOMINGO"
    );

    @Override
    public List<HorarioDisponibleDTO> calcularDisponibilidad(Integer idBarbero, LocalDate fecha, List<Integer> idServicios) {

        int duracionRequerida = servicioRepository.findAllById(idServicios).stream()
                .mapToInt(Servicio::getDuracionMinuto).sum();

        if (duracionRequerida == 0) {
            return new ArrayList<>(); // Si no hay servicios, no hay horarios que mostrar
        }

        // ======================= INICIO DE LA CORRECCIÓN =======================
        //
        // La lógica original buscaba un horario por día de la semana, pero la BD no lo soporta.
        // REEMPLAZAMOS esa búsqueda por una que busca cualquier horario genérico del barbero.
        // Esto hará que funcione con la estructura actual de la base de datos.
        //
        Horario horarioDelDia = horarioRepository.findFirstByBarberoId(idBarbero)
                .orElseThrow(() -> new RecursoNoEncontradoException("El barbero no tiene un horario de trabajo configurado en el sistema."));
        //
        // ======================== FIN DE LA CORRECCIÓN =========================

        LocalTime horaInicioTrabajo = horarioDelDia.getHoraEntrada();
        LocalTime horaFinTrabajo = horarioDelDia.getHoraSalida();
        // NOTA: Si `getTiempoSesion()` da error de NullPointer, podría ser un problema de carga de datos.
        int intervaloMinutos = horarioDelDia.getTiempoSesion().getIntervaloSesionMinutos();

        List<Cita> citasExistentes = citaRepository.findByBarbero_IdAndFecha(idBarbero, fecha);
        List<HorarioDisponibleDTO> disponibilidad = new ArrayList<>();
        LocalTime horaActual = horaInicioTrabajo;

        while (horaActual.plusMinutes(duracionRequerida).isBefore(horaFinTrabajo.plusSeconds(1))) {
            boolean estaDisponible = true;
            LocalTime horaFinPotencial = horaActual.plusMinutes(duracionRequerida);

            for (Cita cita : citasExistentes) {
                LocalTime inicioCita = cita.getHora();
                LocalTime finCita = inicioCita.plusMinutes(cita.getDuracionTotalMinutos());
                if (horaActual.isBefore(finCita) && horaFinPotencial.isAfter(inicioCita)) {
                    estaDisponible = false;
                    break;
                }
            }

            disponibilidad.add(new HorarioDisponibleDTO(horaActual, estaDisponible));
            horaActual = horaActual.plusMinutes(intervaloMinutos);
        }

        return disponibilidad;
    }
}