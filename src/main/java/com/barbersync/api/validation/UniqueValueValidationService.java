// src/main/java/com/barbersync/api/validations/UniqueValueValidationService.java
package com.barbersync.api.validation;

/**
 * Interfaz para servicios que pueden verificar si un valor ya existe
 * para un campo específico en la base de datos.
 */
public interface UniqueValueValidationService {
    /**
     * Verifica si un valor ya existe para un campo dado.
     * @param fieldName El nombre del campo/columna a verificar (e.g., "correo", "telefono").
     * @param value El valor a verificar.
     * @return true si el valor ya existe, false en caso contrario.
     */
    boolean fieldValueExists(String fieldName, Object value);
}