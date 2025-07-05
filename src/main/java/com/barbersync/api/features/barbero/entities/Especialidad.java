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

    private String nombre;
}
