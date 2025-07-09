package com.barbersync.api.features.barbero.entities;

import com.barbersync.api.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "barbero_especialidad")
public class BarberoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_barbero")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;
}
