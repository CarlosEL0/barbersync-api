package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;

public class CitaMapper {
    public static CitaResponse toResponse(Cita cita) {

        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setFecha(cita.getFecha());
        response.setHora(cita.getHora());
        response.setDuracionTotalMinutos(cita.getDuracionTotalMinutos());

        if (cita.getCliente() != null) {
            response.setIdCliente(cita.getCliente().getId());
            response.setNombreCliente(
                    cita.getCliente().getPrimerNombre() + " " + cita.getCliente().getPrimerApellido()
            );
        }

        if (cita.getBarbero() != null) {
            response.setIdBarbero(cita.getBarbero().getId());
            response.setNombreBarbero(
                    cita.getBarbero().getPrimerNombre() + " " + cita.getBarbero().getPrimerApellido()
            );
        }

        if (cita.getEstadoCita() != null) {
            response.setEstado(cita.getEstadoCita().getNombreEstado());
        }

        return response;
    }

    public static Cita toEntity(CitaRequest request) {
        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        return cita;
    }
}
