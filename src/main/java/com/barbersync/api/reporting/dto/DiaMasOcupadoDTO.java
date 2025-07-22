package com.barbersync.api.reporting.dto;

public record DiaMasOcupadoDTO(
        String diaSemana, // Ej: "Lunes", "Martes"
        Long totalCitas
) {}