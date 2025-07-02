package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CitaMapper {
    @Mapping(source = "usuario.id", target = "idCliente")
    @Mapping(source = "usuario.id", target = "idBarbero")
    @Mapping(source = "estado_cita.nombre_estado", target = "nombreEstado")
    Cita citaRequestToCita(CitaRequest citaRequest);

    @Mapping(source = "usuario.id", target = "idCliente")
    @Mapping(source = "usuario.id", target = "idBarbero")
    @Mapping(source = "estado_cita.nombre_estado", target = "estado")
    CitaResponse citaToCitaResponse(CitaResponse citaResponse);
}