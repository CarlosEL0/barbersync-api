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

    List<Cita> findByIdCliente(Usuario cliente);

    List<Cita> findByIdBarbero(Usuario barbero);

    List<Cita> findByIdEstadoCita(EstadoCita estado);

    List<Cita> findByIdBarberoAndFecha(Usuario barbero, LocalDate fecha);

    List<Cita> findByIdClienteAndFecha(Usuario cliente, LocalDate fecha);

    List<Cita> findByFechaBetween(LocalDate inicio, LocalDate fin);

    List<Cita> findByIdBarberoAndFechaBetween(Usuario barbero, LocalDate inicio, LocalDate fin);
}
