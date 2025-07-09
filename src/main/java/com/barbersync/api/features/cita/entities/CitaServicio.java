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
    @MapsId("idCita")  // Relacionamos la clave primaria compuesta con la entidad Cita
    @JoinColumn(name = "id_cita")
    private Cita cita;  // Relación con la entidad Cita

    @ManyToOne
    @MapsId("idServicio")
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;  // Relación con la entidad Servicio
}