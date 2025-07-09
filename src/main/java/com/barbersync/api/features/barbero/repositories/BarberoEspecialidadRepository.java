package com.barbersync.api.features.barbero.repositories;

import com.barbersync.api.features.barbero.entities.BarberoEspecialidad;
import com.barbersync.api.features.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberoEspecialidadRepository extends JpaRepository<BarberoEspecialidad, Integer> {
    List<BarberoEspecialidad> findByUsuario(Usuario usuario);
}
