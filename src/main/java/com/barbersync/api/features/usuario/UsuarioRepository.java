package com.barbersync.api.features.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Importar List
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);
    Boolean existsByCorreo(String correo);

    // --- METOODO CORREGIDO ---
    // El nombre ahora es findBy[Entidad] [CampoEnLaEntidad] [Operador]
    // findBy + Rol + Rol + IgnoreCase
    List<Usuario> findByRolRolIgnoreCase(String nombreDelRol);

}