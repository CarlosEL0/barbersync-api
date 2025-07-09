package com.barbersync.api.features.resena.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResenaResponse {

    private Integer id;
    private Integer calificacion;
    private String comentario;
    private String nombreCliente;
    private String nombreBarbero;
    private LocalDate fechaResena;
}
