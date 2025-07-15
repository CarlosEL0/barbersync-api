// Archivo: ResenaRequest.java

package com.barbersync.api.features.resena.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResenaRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer calificacion;

    @NotBlank
    private String comentario;

    // AÑADIR ESTE CAMPO
    @NotBlank // O @NotNull, dependiendo de si quieres permitir una cadena vacía
    private String fechaResena; // Lo recibimos como String para ser flexibles

    @NotNull
    private Integer idCliente;

    @NotNull
    private Integer idBarbero;
}