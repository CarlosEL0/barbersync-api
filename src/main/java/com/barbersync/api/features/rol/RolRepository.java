package com.barbersync.api.features.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    // --- NUEVO MÉTODO A AÑADIR (si no lo tienes ya) ---
    // Spring Data JPA entenderá que debe buscar por el campo 'rol' de la entidad 'Rol'
    Optional<Rol> findByRol(String nombreDelRol);
}
