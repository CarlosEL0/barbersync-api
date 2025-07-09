package com.barbersync.api.shared.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja las excepciones de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        // Devolver los errores con un código HTTP 400 (Bad Request)
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción de recurso no encontrado
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Object> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex) {
        // Devolver un mensaje con un código HTTP 404 (Not Found)
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Maneja excepciones generales de la aplicación (errores no controlados)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // Devolver un mensaje con un código HTTP 500 (Internal Server Error)
        return new ResponseEntity<>("Ocurrió un error interno en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Maneja excepciones de violación de integridad de datos (por ejemplo, violación de claves foráneas)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Mensaje detallado sobre la violación de integridad (puedes personalizarlo según tu necesidad)
        return new ResponseEntity<>("Error de integridad de datos: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Maneja errores de tipo NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        // Retornar un mensaje indicando que hubo un intento de acceder a un objeto nulo
        return new ResponseEntity<>("Intento de acceso a un objeto nulo", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
