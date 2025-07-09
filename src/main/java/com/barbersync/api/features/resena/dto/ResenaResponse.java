package com.barbersync.api.features.resena.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResenaResponse {

    private Integer id;
    private Integer calificacion;
    private String comentario;
    private String nombreCliente;
    private String nombreBarbero;
    private LocalDate fechaResena;
}
