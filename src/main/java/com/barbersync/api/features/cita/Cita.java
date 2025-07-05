package com.barbersync.api.features.cita;

import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.cita.entities.EstadoCita;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "cita")
public class Cita {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

 private LocalDate fecha;

 private LocalTime hora;

 @ManyToOne
 @JoinColumn(name = "id_estado_cita", referencedColumnName = "id")
 private EstadoCita estadoCita;

 @ManyToOne
 @JoinColumn(name = "id_cliente", referencedColumnName = "id")
 private Usuario cliente;

 @ManyToOne
 @JoinColumn(name = "id_barbero", referencedColumnName = "id")
 private Usuario barbero;

 @Column(name = "duracion_total_minutos")
 private Integer duracionTotalMinutos;
}
