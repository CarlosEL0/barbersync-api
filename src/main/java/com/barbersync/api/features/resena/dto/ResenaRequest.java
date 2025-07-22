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

    @NotBlank // Puedes usar @NotNull si no necesitas validar que tenga texto
    private String fechaResena;

    @NotNull
    private Integer idCliente;

    @NotNull
    private Integer idBarbero;

    // ✅ NUEVO CAMPO NECESARIO PARA VINCULAR LA CITA
    @NotNull
    private Integer idCita;
}
