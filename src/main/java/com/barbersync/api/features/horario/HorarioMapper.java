package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.dto.HorarioResponse;
import com.barbersync.api.features.horario.entities.TiempoSesion;

public class HorarioMapper {

    public static HorarioResponse toResponse(Horario horario) {
        HorarioResponse dto = new HorarioResponse();
        dto.setId(horario.getId());
        dto.setIdBarbero(horario.getBarbero().getId());
        dto.setNombreBarbero(horario.getBarbero().getPrimerNombre() + " " + horario.getBarbero().getPrimerApellido());
        dto.setHoraEntrada(horario.getHoraEntrada());
        dto.setHoraSalida(horario.getHoraSalida());

        TiempoSesion sesion = horario.getTiempoSesion();
        dto.setDiaLaboral(sesion.getDiaLaboral());
        dto.setIntervaloSesionMinutos(sesion.getIntervaloSesionMinutos());

        return dto;
    }
}
