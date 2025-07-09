package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.entities.CitaServicio;
import com.barbersync.api.features.cita.entities.CitaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaServicioRepository extends JpaRepository<CitaServicio, CitaServicioId> {
    // Metodo para eliminar todos los servicios asociados a una cita por el ID de la cita
    void deleteAllByCita_Id(Integer idCita);  // Usamos la relación "Cita" y su "id"
}
