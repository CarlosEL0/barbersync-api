package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByCliente_Id(Integer idCliente);
    List<Cita> findByBarbero_Id(Integer idBarbero);
    List<Cita> findByEstadoCita_NombreEstado(String estado);
    List<Cita> findByFecha(LocalDate fecha);
    List<Cita> findByBarbero_IdAndFecha(Integer idBarbero, LocalDate fecha);
    List<Cita> findByCliente_IdAndEstadoCita_NombreEstado(Integer idCliente, String estado);
}
