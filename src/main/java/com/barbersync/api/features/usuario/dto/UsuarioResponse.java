package com.barbersync.api.features.usuario.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private LocalDate fechaRegistro;
    private Integer rolId;
    private String rolNombre; // ← 👈 Aquí agregamos el nombre del rol
}
