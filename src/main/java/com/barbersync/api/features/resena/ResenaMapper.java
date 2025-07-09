package com.barbersync.api.features.resena;

import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;

public class ResenaMapper {

    public static ResenaResponse toResponse(Resena resena) {
        ResenaResponse dto = new ResenaResponse();
        dto.setId(resena.getId());
        dto.setCalificacion(resena.getCalificacion());
        dto.setComentario(resena.getComentario());
        dto.setFechaResena(resena.getFechaResena());

        // Usamos los nuevos nombres de las propiedades (cliente y barbero)
        if (resena.getCliente() != null) {
            dto.setNombreCliente(
                    resena.getCliente().getPrimerNombre() + " " +
                            resena.getCliente().getPrimerApellido()
            );
        }

        if (resena.getBarbero() != null) {
            dto.setNombreBarbero(
                    resena.getBarbero().getPrimerNombre() + " " +
                            resena.getBarbero().getPrimerApellido()
            );
        }

        return dto;
    }

    public static Resena toEntity(ResenaRequest request) {
        Resena resena = new Resena();
        resena.setCalificacion(request.getCalificacion());
        resena.setComentario(request.getComentario());
        resena.setFechaResena(java.time.LocalDate.now());
        return resena;
    }
}
