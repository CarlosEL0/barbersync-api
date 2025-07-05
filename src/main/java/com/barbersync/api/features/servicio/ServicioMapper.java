package com.barbersync.api.features.servicio;

import com.barbersync.api.features.servicio.dto.ServicioRequest;
import com.barbersync.api.features.servicio.dto.ServicioResponse;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    public Servicio toEntity(ServicioRequest request) {
        Servicio servicio = new Servicio();
        servicio.setNombre(request.getNombre());
        servicio.setDescripcion(request.getDescripcion());
        servicio.setPrecio(request.getPrecio());
        servicio.setDuracionMinuto(request.getDuracionMinuto());
        return servicio;
    }

    public ServicioResponse toResponse(Servicio servicio) {
        ServicioResponse response = new ServicioResponse();
        response.setId(servicio.getId());
        response.setNombre(servicio.getNombre());
        response.setDescripcion(servicio.getDescripcion());
        response.setPrecio(servicio.getPrecio());
        response.setDuracionMinuto(servicio.getDuracionMinuto());
        return response;
    }

    public void updateEntityFromRequest(Servicio servicio, ServicioRequest request) {
        servicio.setNombre(request.getNombre());
        servicio.setDescripcion(request.getDescripcion());
        servicio.setPrecio(request.getPrecio());
        servicio.setDuracionMinuto(request.getDuracionMinuto());
    }
}
