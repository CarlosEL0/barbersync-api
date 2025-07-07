package com.barbersync.api.features.cita.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class CitaServicioId implements Serializable {
    private Integer idCita;
    private Integer idServicio;
}

