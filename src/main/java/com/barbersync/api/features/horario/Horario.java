package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.entities.TiempoSesion;
import com.barbersync.api.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "horario") // Asegura que JPA use la tabla 'horario' (en minúsculas)
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Relación con el barbero (Usuario).
     * 🔧 Solución al problema: la columna en la base de datos se llama 'id_barbero',
     * pero Hibernate intentaba buscar 'barbero_id'.
     * Usamos @JoinColumn(name = "id_barbero") para indicarle explícitamente el nombre correcto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_barbero", referencedColumnName = "id", nullable = false)
    private Usuario barbero;

    @Column(name = "hora_entrada")
    private LocalTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalTime horaSalida;

    @ManyToOne
    @JoinColumn(name = "tiempo_sesion_id", referencedColumnName = "id")
    private TiempoSesion tiempoSesion;
}
