package com.barbersync.api.features.usuario.dto;

import lombok.Data;

@Data

public class UsuarioRequest {
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String contrasena;
    private Integer rolId;
    // el rol se asignará por defecto o en otro proceso
}