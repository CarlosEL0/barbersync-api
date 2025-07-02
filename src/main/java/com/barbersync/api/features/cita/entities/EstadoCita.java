package com.barbersync.api.features.cita.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "estado_cita")

public class EstadoCita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_estado")
    private String nombreEstado;
}