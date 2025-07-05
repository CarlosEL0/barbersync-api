package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CitaMapper {

    @Mapping(source = "cliente.id", target = "idCliente")
    @Mapping(source = "barbero.id", target = "idBarbero")
    @Mapping(source = "estadoCita.nombreEstado", target = "estado")
    CitaResponse toResponse(Cita cita);
}
