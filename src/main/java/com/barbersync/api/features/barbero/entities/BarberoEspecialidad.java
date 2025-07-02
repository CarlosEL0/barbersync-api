package com.barbersync.api.features.barbero.entities;

import com.barbersync.api.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "barbero_especialidad")

public class BarberoEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_barbero", referencedColumnName = "id")
    private Usuario idBarbero;

    @ManyToOne
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    private Especialidad idEspecialidad;

}
