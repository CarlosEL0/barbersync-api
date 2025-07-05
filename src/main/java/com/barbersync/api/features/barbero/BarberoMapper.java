package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.dto.BarberoResponseDto;
import com.barbersync.api.features.barbero.entities.Barbero;
import com.barbersync.api.features.barbero.entities.BarberoEspecialidad;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BarberoMapper {

    public BarberoResponseDto toResponse(Barbero barbero) {
        BarberoResponseDto dto = new BarberoResponseDto();
        dto.setId(barbero.getId());
        dto.setCorreo(barbero.getUsuario().getCorreo());

        String nombreCompleto = barbero.getUsuario().getPrimerNombre() + " "
                + (barbero.getUsuario().getSegundoNombre() != null ? barbero.getUsuario().getSegundoNombre() + " " : "")
                + barbero.getUsuario().getPrimerApellido() + " "
                + (barbero.getUsuario().getSegundoApellido() != null ? barbero.getUsuario().getSegundoApellido() : "");
        dto.setNombreCompleto(nombreCompleto.trim());

        List<String> especialidades = barbero.getEspecialidades().stream()
                .map(be -> be.getEspecialidad().getNombre())
                .collect(Collectors.toList());
        dto.setEspecialidades(especialidades);

        return dto;
    }
}
