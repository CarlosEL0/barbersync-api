package com.barbersync.api.features.cita.entities;

import com.barbersync.api.features.cita.Cita;
import com.barbersync.api.features.servicio.Servicio;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cita_servicio")
public class CitaServicio {

    @EmbeddedId
    private CitaServicioId id;

    @ManyToOne
    @MapsId("idCita")
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @ManyToOne
    @MapsId("idServicio")
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;
}
