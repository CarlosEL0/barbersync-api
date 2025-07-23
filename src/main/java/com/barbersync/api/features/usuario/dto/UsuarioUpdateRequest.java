package com.barbersync.api.features.usuario.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

// Este DTO representa los datos que se pueden cambiar en una actualización de perfil.
// No incluimos campos sensibles o que no se deberían cambiar aquí, como la contraseña.
@Getter
@Setter
public class UsuarioUpdateRequest {

    // El nombre es opcional, si no se envía no se actualiza
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;

    // Podríamos querer mantener la validación del correo si se permite cambiar
    @Email(message = "El formato del correo no es válido")
    private String correo;

    // NOTA: 'contrasena' y 'rolId' se omiten a propósito.
    // La contraseña se maneja en un endpoint aparte y el rol no se modifica
    // desde un perfil de usuario normal.
}