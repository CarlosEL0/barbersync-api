package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.entities.CitaServicio;
import com.barbersync.api.features.cita.entities.CitaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaServicioRepository extends JpaRepository<CitaServicio, CitaServicioId> {
}
