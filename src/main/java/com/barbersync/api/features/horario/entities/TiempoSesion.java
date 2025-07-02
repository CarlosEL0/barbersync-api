package com.barbersync.api.features.horario.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tiempo_sesion")

public class TiempoSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "intervalo_sesion_minutos")
    private Integer intervaloSesionMinutos;

    @Column(name = "dia_laboral")
    private String diaLaboral;
}
