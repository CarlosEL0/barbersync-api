// Ubicación: com/barbersync/api/features/disponibilidad/dto/DisponibilidadRequest.java
package com.barbersync.api.features.disponibilidad.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data // Anotación de Lombok para getters y setters
public class DisponibilidadRequest {
    private LocalDate fecha;
    private List<Integer> idServicios;
}