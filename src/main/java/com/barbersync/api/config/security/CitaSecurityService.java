// Ubicación: src/main/java/com/barbersync/api/config/security/CitaSecurityService.java
package com.barbersync.api.config.security;

import com.barbersync.api.auth.services.impl.CustomUserDetails;
import com.barbersync.api.features.cita.Cita;
import com.barbersync.api.features.cita.CitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This service provides custom security checks for Cita entities.
 * The bean is named "citaSecurityService" so it can be called from @PreAuthorize annotations.
 */
@Service("citaSecurityService") // <-- ¡El nombre del bean es crucial! Debe coincidir con el de @PreAuthorize.
@RequiredArgsConstructor
public class CitaSecurityService {

    private final CitaRepository citaRepository;

    /**
     * Checks if the currently authenticated user is either the client or the barber
     * associated with a specific Cita.
     * This method is "bulletproof": it will NOT throw an exception if the Cita is not found,
     * it will simply return false.
     *
     * @param authentication The current user's authentication object, provided by Spring Security.
     * @param idCita The ID of the Cita to check.
     * @return true if the user is involved in the Cita, false otherwise.
     */
    public boolean estaInvolucradoEnCita(Authentication authentication, Integer idCita) {
        // Step 1: Get the details of the currently logged-in user.
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer idUsuarioLogueado = userDetails.getId(); // Assuming getId() returns Integer. Adjust if it's Long.

        // Step 2: Find the Cita in the database, but do it safely using Optional.
        Optional<Cita> citaOptional = citaRepository.findById(idCita);

        // Step 3: CRITICAL CHECK! If the Cita does not exist, the user cannot be involved. Return false.
        // This prevents the NullPointerException that was causing the 500 Error.
        if (citaOptional.isEmpty()) {
            return false;
        }

        // Step 4: If we get here, the Cita exists. Get it from the Optional.
        Cita cita = citaOptional.get();

        // Step 5: Check if the logged-in user's ID matches the Cita's client ID or barber ID.
        boolean esElCliente = cita.getCliente().getId().equals(idUsuarioLogueado);
        boolean esElBarbero = cita.getBarbero().getId().equals(idUsuarioLogueado);

        return esElCliente || esElBarbero;
    }
}