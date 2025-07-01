package com.barbersync.api.features.usuario;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    private String correo;

    @Column(name = "fecha_registro")
    private LocalTime fechaRegistro;

    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Usuario roles;
}