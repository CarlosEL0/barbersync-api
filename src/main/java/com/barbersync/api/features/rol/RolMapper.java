package com.barbersync.api.features.rol;

import com.barbersync.api.features.rol.dto.RolRequest;
import com.barbersync.api.features.rol.dto.RolResponse;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public Rol toEntity(RolRequest request) {
        Rol rol = new Rol();
        rol.setRol(request.getRol());  // Cambiado de setNombre a setRol
        return rol;
    }

    public RolResponse toResponse(Rol rol) {
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setRol(rol.getRol());  // Cambiado de getNombre a getRol
        return response;
    }

    public void updateEntityFromRequest(Rol rol, RolRequest request) {
        rol.setRol(request.getRol());  // Cambiado de setNombre a setRol
    }
}

