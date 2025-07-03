package com.barbersync.api.features.rol;

import com.barbersync.api.features.rol.dto.RolRequest;
import com.barbersync.api.features.rol.dto.RolResponse;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public Rol toEntity(RolRequest request) {
        Rol rol = new Rol();
        rol.setNombre(request.getNombre());
        return rol;
    }

    public RolResponse toResponse(Rol rol) {
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        return response;
    }

    public void updateEntityFromRequest(Rol rol, RolRequest request) {
        rol.setNombre(request.getNombre());
    }
}
