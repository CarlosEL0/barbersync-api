package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.entities.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    // Puedes agregar métodos personalizados si luego necesitas filtrado por barbero, fecha, etc.
}
