package com.barbersync.api.reporting.dto;

public record ServicioMasAgendadoDTO(
        String nombreServicio,
        Long totalAgendado
) {}