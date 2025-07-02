package com.barbersync.api.features.usuario;

import com.barbersync.api.features.rol.Rol;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
    private LocalDate fechaRegistro;

    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    // ✅ Agrega este método auxiliar si solo tienes el ID del rol
    public void setRolId(Integer rolId) {
        if (this.rol == null) {
            this.rol = new Rol();
        }
        this.rol.setId(rolId);
    }

    public Integer getRolId() {
        return this.rol != null ? this.rol.getId() : null;
    }
}
