package com.barbersync.api.features.barbero.entities;

import com.barbersync.api.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "barbero")
public class Barbero {

    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "barbero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BarberoEspecialidad> especialidades;
}
