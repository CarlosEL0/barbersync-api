package com.barbersync.api.features.barbero.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "barbero_especialidad")

public class BarberoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    private Especialidad idEspecialidad;



}
