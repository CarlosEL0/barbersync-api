package com.barbersync.api.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Esta es nuestra "alarma" personalizada.
 * Es una clase que define un nuevo tipo de error específico para nuestra aplicación.
 * La lanzaremos desde el `UsuarioService` cuando detectemos un correo duplicado.
 *
 * La anotación @ResponseStatus le dice a Spring que si esta excepción llega hasta
 * el final sin ser manejada, debe devolver automáticamente un código HTTP 409 (Conflict),
 * que es el código correcto para "el recurso que intentas crear/modificar ya existe".
 */
@ResponseStatus(HttpStatus.CONFLICT) // <-- Responde con un HTTP 409
public class CorreoYaExisteException extends RuntimeException {

    /**
     * Este es el constructor de nuestra alarma.
     * Acepta un mensaje de texto para que podamos decir exactamente cuál fue el problema.
     * Por ejemplo: "El correo 'juan@perez.com' ya está en uso."
     * @param message El mensaje de error que se mostrará.
     */
    public CorreoYaExisteException(String message) {
        // 'super(message)' simplemente le pasa el mensaje a la clase padre (RuntimeException)
        // para que lo gestione.
        super(message);
    }
}