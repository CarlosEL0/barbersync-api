package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.entities.EstadoCita;
import com.barbersync.api.features.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByFecha(LocalDate fecha);
    List<Cita> findByHora(LocalTime hora);

    List<Cita> findByCliente(Usuario cliente);
    List<Cita> findByBarbero(Usuario barbero);
    List<Cita> findByEstadoCita(EstadoCita estado);

    List<Cita> findByClienteAndFecha(Usuario cliente, LocalDate fecha);
    List<Cita> findByFechaBetween(LocalDate inicio, LocalDate fin);
    List<Cita> findByBarberoAndFechaBetween(Usuario barbero, LocalDate inicio, LocalDate fin);
}
