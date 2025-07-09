package com.barbersync.api.features.rol;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rol") // Actualizado para reflejar el nombre correcto de la columna en la base de datos
    private String rol;  // Cambiado de 'nombre' a 'rol'
}
