package com.barbersync.api.features.disponibilidad;

import com.barbersync.api.features.disponibilidad.dto.HorarioDisponibleDTO;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadService {

    /**
     * Calcula los horarios disponibles para un barbero en una fecha específica,
     * considerando la duración total de los servicios seleccionados.
     *
     * @param idBarbero   El ID del barbero.
     * @param fecha       La fecha para la cual se consulta la disponibilidad.
     * @param idServicios La lista de IDs de los servicios seleccionados por el cliente.
     * @return Una lista de bloques de tiempo con su estado de disponibilidad.
     */
    List<HorarioDisponibleDTO> calcularDisponibilidad(Integer idBarbero, LocalDate fecha, List<Integer> idServicios);

}