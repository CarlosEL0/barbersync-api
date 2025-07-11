package com.barbersync.api.auth.dto;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.barbersync.api.validation.UniqueEmail;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class RegisterRequestDto {
    @NotBlank(message = "El primer nombre es obligatorio")
    private String primerNombre;

    private String segundoNombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;

    private String segundoApellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "el formato de correo no coincide")
    @UniqueEmail
    private String email;

    @NotBlank(message = "se debe crear una contrasena")
    @Size(min = 8, message = "la contrasena debe tener al menos 8 caracteres")
    private String contrasena;
}
