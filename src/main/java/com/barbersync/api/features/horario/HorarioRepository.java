package com.barbersync.api.features.horario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    // Puedes agregar métodos personalizados aquí si los necesitas
}
