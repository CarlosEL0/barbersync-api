package com.barbersync.api.features.horario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {

    // Metodo original, lo dejamos por si se usa en otro lado o para el futuro.
    Optional<Horario> findByBarberoIdAndTiempoSesionDiaLaboral(Integer barberoId, String diaSemana);

    // --- ¡ESTE ES EL METODO NUEVO Y CLAVE! ---
    // Busca el primer horario genérico que encuentre para un barbero, sin importar el día.
    // Lo usaremos como solución temporal para que la disponibilidad funcione YA.
    Optional<Horario> findFirstByBarberoId(Integer barberoId);
}