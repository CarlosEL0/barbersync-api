package com.barbersync.api.features.barbero;

import com.barbersync.api.features.barbero.entities.BarberoEspecialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberoEspecialidadRepository extends JpaRepository<BarberoEspecialidad, Integer> {
}
