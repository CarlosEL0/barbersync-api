package com.barbersync.api.features.servicio;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "servicio")

public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    @Column(name = "duracion_minuto")
    private Integer duracionMinuto;




}