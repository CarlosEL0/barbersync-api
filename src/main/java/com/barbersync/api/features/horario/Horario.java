package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.entities.TiempoSesion;
import com.barbersync.api.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "horario")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_barbero", referencedColumnName = "id")
    private Usuario barbero;

    @Column(name = "hora_entrada")
    private LocalTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalTime horaSalida;

    @ManyToOne
    @JoinColumn(name = "tiempo_sesion_id", referencedColumnName = "id")
    private TiempoSesion tiempoSesion;
}
