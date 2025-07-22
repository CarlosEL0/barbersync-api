// Archivo: com/barbersync/api/features/resena/Resena.java (VERSIÓN ACTUALIZADA)
package com.barbersync.api.features.resena;

import com.barbersync.api.features.cita.Cita; // <-- IMPORTANTE: Añade este import
import com.barbersync.api.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_barbero")
    private Usuario barbero;

    // ✅✅✅ LA RELACIÓN CLAVE QUE AÑADIMOS ✅✅✅
    // Es OneToOne porque una Cita solo puede tener una Reseña.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", referencedColumnName = "id", unique = true) // 'unique = true' refleja la restricción de la BD
    private Cita cita;
}