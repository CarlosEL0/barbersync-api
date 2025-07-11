package com.barbersync.api.features.usuario.dto;

import com.barbersync.api.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "El primer nombre es obligatorio")
    private String primerNombre;

    private String segundoNombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;

    private String segundoApellido;

    @Email(message = "Debe ser un correo válido")
    @UniqueEmail
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "la contrasena debe tener al menos 8 caracteres")
    private String contrasena;

    private Integer rolId;
}
