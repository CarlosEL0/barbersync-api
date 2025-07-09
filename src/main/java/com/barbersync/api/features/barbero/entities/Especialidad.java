package com.barbersync.api.features.barbero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "especialidad")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "especialidad") // <-- Corrige el nombre del campo
    private String nombre;

    private String descripcion; // <-- Agrega este campo que también existe en la BD
}
