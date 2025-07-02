package com.barbersync.api.features.cita.entities;

import com.barbersync.api.features.servicio.Servicio;
import com.barbersync.api.features.cita.Cita;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cita_servicio")

public class CitaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cita", referencedColumnName = "id")
    private Cita idCita;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    private Servicio idServicio;
}
