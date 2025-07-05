package com.barbersync.api.features.horario;

import com.barbersync.api.features.horario.entities.TiempoSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiempoSesionRepository extends JpaRepository<TiempoSesion, Integer> {
}
