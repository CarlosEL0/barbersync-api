package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // --- Versiones mejoradas con JOIN FETCH para evitar LazyInitializationException ---

    @Query("SELECT c FROM Cita c JOIN FETCH c.cliente JOIN FETCH c.barbero JOIN FETCH c.estadoCita WHERE c.cliente.id = :idCliente")
    List<Cita> findByCliente_Id(@Param("idCliente") Integer idCliente);

    @Query("SELECT c FROM Cita c JOIN FETCH c.cliente JOIN FETCH c.barbero JOIN FETCH c.estadoCita WHERE c.barbero.id = :idBarbero")
    List<Cita> findByBarbero_Id(@Param("idBarbero") Integer idBarbero);

    @Query("SELECT c FROM Cita c JOIN FETCH c.cliente JOIN FETCH c.barbero JOIN FETCH c.estadoCita WHERE c.fecha = :fecha")
    List<Cita> findByFecha(@Param("fecha") LocalDate fecha);

    // Consulta específica y optimizada para el caso que falla
    @Query("SELECT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "WHERE c.barbero.id = :idBarbero AND c.fecha = :fecha")
    List<Cita> findByBarbero_IdAndFecha(@Param("idBarbero") Integer idBarbero, @Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM Cita c JOIN FETCH c.cliente JOIN FETCH c.barbero JOIN FETCH c.estadoCita WHERE c.cliente.id = :idCliente AND c.estadoCita.nombreEstado = :estado")
    List<Cita> findByCliente_IdAndEstadoCita_NombreEstado(@Param("idCliente") Integer idCliente, @Param("estado") String estado);

    // Dejamos esta sin modificar por si la necesitas en algún otro contexto
    List<Cita> findByEstadoCita_NombreEstado(String estado);
}