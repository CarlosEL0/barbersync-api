package com.barbersync.api.features.resena;

import java.time.LocalDate;
import lombok.Data;
import jakarta.persistence.*;
import com.barbersync.api.features.usuario.Usuario;

@Entity
@Data
@Table(name = "resena")

public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer calificacion;

    private String comentario;

    @Column(name = "fecha_resena")
    private LocalDate fechaResena;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Usuario idCliente;

    @ManyToOne
    @JoinColumn(name = "id_barbero", referencedColumnName = "id")
    private Usuario idBarbero;
}
