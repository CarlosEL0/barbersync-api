package com.barbersync.api.features.resena;

import com.barbersync.api.features.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    List<Resena> findByCalificacion(Integer calificacion);

    List<Resena> findByCalificacionGreaterThanEqual(Integer calificacion);

    // Cambiar 'idCliente' por 'cliente'
    List<Resena> findByCliente(Usuario cliente);

    // Cambiar 'idBarbero' por 'barbero'
    List<Resena> findByBarbero(Usuario barbero);

    // Modificar el nombre del método para usar 'cliente' en lugar de 'idCliente'
    List<Resena> findByClienteAndCalificacionGreaterThanEqual(Usuario cliente, Integer calificacion);

    List<Resena> findByFechaResena(LocalDate fecha);

    List<Resena> findByFechaResenaBetween(LocalDate startDate, LocalDate endDate);
}
