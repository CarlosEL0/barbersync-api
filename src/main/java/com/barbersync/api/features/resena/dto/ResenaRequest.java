package com.barbersync.api.features.resena.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResenaRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer calificacion;

    @NotBlank
    private String comentario;
    
    private Integer idCliente;
    private Integer idBarbero;
}