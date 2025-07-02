package com.barbersync.api.features.servicio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    Optional<Servicio> findByNombre(String nombre);

    List<Servicio> findByPrecioLessThanEqual(double precio);

    List<Servicio> findByDuracionMinutoGreaterThan(Integer duracionMinuto);

    Boolean existsByNombre(String nombre);


}