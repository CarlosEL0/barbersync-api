package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.entities.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {
}
