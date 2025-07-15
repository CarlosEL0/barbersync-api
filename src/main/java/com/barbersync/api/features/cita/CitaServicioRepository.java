package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.entities.CitaServicio;
import com.barbersync.api.features.cita.entities.CitaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaServicioRepository extends JpaRepository<CitaServicio, CitaServicioId> {

    // Eliminar todos los registros relacionados a una cita (relación muchos a muchos)
    void deleteAllByCita_Id(Integer id);

    // Obtener todos los servicios relacionados con una cita
    List<CitaServicio> findAllByCita_Id(Integer id);
}
