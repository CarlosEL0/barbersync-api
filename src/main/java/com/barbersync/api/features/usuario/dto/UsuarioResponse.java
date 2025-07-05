package com.barbersync.api.features.usuario.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String fechaRegistro;
    private Integer rolId;
}
