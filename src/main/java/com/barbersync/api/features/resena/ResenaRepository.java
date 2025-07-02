package com.barbersync.api.features.resena;

import com.barbersync.api.features.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository <Resena, Integer>{

List<Resena> findByCalificacion(Integer calificacion);

List<Resena> findByCalificacionGreaterThanEqual(Integer calificacion);

List<Resena> findByIdCliente(Usuario cliente);

List<Resena> findByIdBarbero(Usuario barbero);

List<Resena> findByIdClienteAndCalificacionGreaterThanEqual(Usuario cliente, Integer calificacion);

List<Resena> findByFechaResena(LocalDate fecha);
List<Resena> findByFechaResenaBetween(LocalDate startDate, LocalDate endDate);
}

