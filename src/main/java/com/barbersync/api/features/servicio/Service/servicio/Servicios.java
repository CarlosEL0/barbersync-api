package com.barbersync.api.features.servicio.Service.servicio;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "servicio")

public class Servicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String descripcion;

    private double precio;

    @Column(name = "duracion_minuto")
    private Integer duracionMinuto;




}