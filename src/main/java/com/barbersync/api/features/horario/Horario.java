package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.entities.TiempoSesion;
import jakarta.persistence.*;
import lombok.Data;
import com.barbersync.api.features.usuario.Usuario;
import java.time.LocalTime;


@Entity
@Data
@Table(name = "horario")

public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario idBarbero;

    @Column(name = "hora_entrada")
    private LocalTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalTime horaSalida;

    @OneToOne
    @JoinColumn(name = "id_tiempo_sesion", referencedColumnName = "id")
    private TiempoSesion tiempoSesion;
}
