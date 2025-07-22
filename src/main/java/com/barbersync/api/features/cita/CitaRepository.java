package com.barbersync.api.features.cita;

import com.barbersync.api.reporting.dto.DiaMasOcupadoDTO;
import com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // --- MÉTODOS ORIGINALES QUE NECESITA TU APLICACIÓN (LOS QUE FALTABAN) ---

    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.cliente.id = :idCliente")
    List<Cita> findByCliente_Id(@Param("idCliente") Integer idCliente);

    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.barbero.id = :idBarbero")
    List<Cita> findByBarbero_Id(@Param("idBarbero") Integer idBarbero);

    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.fecha = :fecha")
    List<Cita> findByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.barbero.id = :idBarbero AND c.fecha = :fecha")
    List<Cita> findByBarbero_IdAndFecha(@Param("idBarbero") Integer idBarbero, @Param("fecha") LocalDate fecha);

    @Query("SELECT DISTINCT c FROM Cita c " +
            "JOIN FETCH c.cliente " +
            "JOIN FETCH c.barbero " +
            "JOIN FETCH c.estadoCita " +
            "LEFT JOIN FETCH c.servicios " +
            "WHERE c.cliente.id = :idCliente AND c.estadoCita.nombreEstado = :estado")
    List<Cita> findByCliente_IdAndEstadoCita_NombreEstado(@Param("idCliente") Integer idCliente, @Param("estado") String estado);

    @Query("SELECT c FROM Cita c WHERE c.estadoCita.nombreEstado IN ('Pendiente', 'Confirmada') AND " +
            "(c.fecha < :fechaActual OR (c.fecha = :fechaActual AND c.hora < :horaActual))")
    List<Cita> findCitasPasadasParaActualizar(@Param("fechaActual") LocalDate fechaActual, @Param("horaActual") LocalTime horaActual);

    List<Cita> findByEstadoCita_NombreEstado(String estado);


    // --- NUEVOS MÉTODOS PARA REPORTES (LOS QUE AÑADIMOS JUNTOS) ---

    @Query("SELECT new com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO(s.nombre, COUNT(c.id)) " +
            "FROM Cita c JOIN c.servicios s " +
            "WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin " +
            "GROUP BY s.nombre " +
            "ORDER BY COUNT(c.id) DESC")
    List<ServicioMasAgendadoDTO> findServiciosMasAgendados(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT new com.barbersync.api.reporting.dto.ServicioMasAgendadoDTO(s.nombre, COUNT(c.id)) " +
            "FROM Cita c JOIN c.servicios s " +
            "GROUP BY s.nombre " +
            "ORDER BY COUNT(c.id) DESC")
    List<ServicioMasAgendadoDTO> findServiciosMasAgendadosHistorico();

    /**
     * Reporte: Cuenta las citas por día de la semana en un rango de fechas.
     * Usa una consulta nativa para máxima compatibilidad con funciones de base de datos como DAYNAME.
     * Los alias 'diaSemana' y 'totalCitas' deben coincidir con los nombres en el record DiaMasOcupadoDTO.
     */
    @Query(value = "SELECT DAYNAME(c.fecha) AS diaSemana, COUNT(c.id) AS totalCitas " +
            "FROM cita c " + // 'cita' debe ser el nombre real de tu tabla en la BD
            "WHERE c.fecha >= :fechaInicio AND c.fecha <= :fechaFin " +
            "GROUP BY diaSemana " + // Agrupamos por el alias
            "ORDER BY totalCitas DESC",
            nativeQuery = true) // <-- La clave es cambiar a una consulta nativa
    List<DiaMasOcupadoDTO> findDiasMasOcupados(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}