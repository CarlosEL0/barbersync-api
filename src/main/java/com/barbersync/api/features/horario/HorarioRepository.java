package com.barbersync.api.features.horario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // <-- Importar Optional

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    // --- ¡AÑADIR ESTE METODO! ---
    // Busca el horario de un barbero para un día específico de la semana (ej. "LUNES")
    // Spring Data JPA creará la consulta automáticamente a partir del nombre del método.
    Optional<Horario> findByBarberoIdAndTiempoSesionDiaLaboral(Integer barberoId, String diaSemana);

}