package com.barbersync.api.features.barbero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "barbero_especialidad")
public class BarberoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "barbero_id")
    private Barbero barbero;

    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private Especialidad especialidad;
}
