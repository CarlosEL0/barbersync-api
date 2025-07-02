package com.barbersync.api.features.resena.dto;

import lombok.Data;

@Data

public class ResenaRequest {

    private Integer calificacion;
    private String comentario;
    private Integer idCliente;
    private Integer idBarbero;

}
