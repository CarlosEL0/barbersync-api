package com.barbersync.api.shared.exceptions;

// =================================================================
// ✅ ¡EL IMPORT QUE FALTABA ESTÁ AQUÍ! ✅
// =================================================================
import com.barbersync.api.shared.exceptions.CorreoYaExisteException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja las excepciones de validación (de @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // Maneja la excepción de recurso no encontrado (404)
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Object> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Manejador para el correo duplicado (¡Ahora funcionará!)
    @ExceptionHandler(CorreoYaExisteException.class)
    public ResponseEntity<Map<String, String>> handleCorreoYaExisteException(CorreoYaExisteException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409
    }

    // Maneja violaciones de integridad de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error de integridad de datos. El recurso puede estar en uso.");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // ... (El resto de tus manejadores de excepciones se quedan igual) ...

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace(); // Es útil para depurar en la consola
        return new ResponseEntity<>("Error interno: Referencia nula encontrada.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        ex.printStackTrace(); // Es útil para depurar en la consola
        return new ResponseEntity<>("Ocurrió un error interno inesperado.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}