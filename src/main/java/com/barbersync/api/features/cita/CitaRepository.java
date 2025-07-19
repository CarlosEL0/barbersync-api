package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // ✅ CLIENTE: trae también los servicios
    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.cliente.id = :idCliente")
    List<Cita> findByCliente_Id(@Param("idCliente") Integer idCliente);

    // ✅ BARBERO: trae también los servicios
    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.barbero.id = :idBarbero")
    List<Cita> findByBarbero_Id(@Param("idBarbero") Integer idBarbero);

    // ✅ FECHA: trae también los servicios
    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.fecha = :fecha")
    List<Cita> findByFecha(@Param("fecha") LocalDate fecha);

    // ✅ BARBERO + FECHA: trae también los servicios
    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.barbero.id = :idBarbero AND c.fecha = :fecha")
    List<Cita> findByBarbero_IdAndFecha(@Param("idBarbero") Integer idBarbero, @Param("fecha") LocalDate fecha);

    // ✅ CLIENTE + ESTADO: trae también los servicios
    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.cliente.id = :idCliente AND c.estadoCita.nombreEstado = :estado")
    List<Cita> findByCliente_IdAndEstadoCita_NombreEstado(@Param("idCliente") Integer idCliente, @Param("estado") String estado);

    // Esta no requiere servicios, por eso no se toca
    List<Cita> findByEstadoCita_NombreEstado(String estado);
}
