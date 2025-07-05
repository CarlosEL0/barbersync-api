package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.entities.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
}
