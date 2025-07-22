package com.barbersync.api.reporting.dto;

// Cambia 'public record' por 'public interface'
public interface DiaMasOcupadoDTO {

    // El nombre del mEeodo debe ser get + AliasDeLaColumnaSQL
    String getDiaSemana(); // Corresponde a `AS diaSemana`

    // COUNT(*) devuelve un tipo numérico grande, usar Number es más seguro
    Number getTotalCitas(); // Corresponde a `AS totalCitas`

}