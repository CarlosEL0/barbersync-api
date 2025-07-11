package com.barbersync.api.validation;

import com.barbersync.api.features.usuario.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Validador: src/main/java/com/barbersync/api/validations/UniqueEmailValidator.java
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true; // Dejamos que @NotBlank se encargue de esto
        }
        return !usuarioRepository.findByCorreo(email).isPresent();
    }
}
