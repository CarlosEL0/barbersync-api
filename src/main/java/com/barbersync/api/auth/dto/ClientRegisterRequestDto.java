package com.barbersync.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Usamos @Data de Lombok para generar getters, setters, etc.
// O puedes generarlos manualmente si prefieres.
@Data
public class ClientRegisterRequestDto {

    @NotBlank(message = "El primer nombre es obligatorio")
    private String primerNombre;

    private String segundoNombre; // Opcional

    @NotBlank(message = "El primer apellido es obligatorio")
    private String primerApellido;

    private String segundoApellido; // Opcional

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    // NOTA IMPORTANTE: Observa que aquí NO existe el campo 'rolId'.
    // Esto impide que el usuario pueda elegir un rol.
}