package com.barbersync.api.features.cita.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "estado_cita")

public class EstadoCita {
    @Id
    private Integer id;
    private String nombre_estado;
}
