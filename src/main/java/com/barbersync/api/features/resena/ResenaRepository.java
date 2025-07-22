package com.barbersync.api.features.resena;

import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.resena.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    List<Resena> findByCalificacion(Integer calificacion);

    List<Resena> findByCalificacionGreaterThanEqual(Integer calificacion);

    List<Resena> findByCliente(Usuario cliente);

    List<Resena> findByBarbero(Usuario barbero);

    List<Resena> findByClienteAndCalificacionGreaterThanEqual(Usuario cliente, Integer calificacion);

    List<Resena> findByFechaResena(LocalDate fecha);

    List<Resena> findByFechaResenaBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.barbero.id = :idBarbero")
    Double obtenerPromedioCalificacionPorBarbero(@Param("idBarbero") Integer idBarbero);

    List<Resena> findByBarberoAndFechaResenaBetween(Usuario barbero, LocalDate inicio, LocalDate fin);

    // ✅ NUEVO MÉTODO PARA VERIFICAR SI UNA CITA YA TIENE UNA RESEÑA
    boolean existsByCita_Id(Integer idCita);
}
