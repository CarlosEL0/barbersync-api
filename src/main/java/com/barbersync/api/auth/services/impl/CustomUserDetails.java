// Ubicación: com/barbersync/api/auth/services/impl/CustomUserDetails.java

package com.barbersync.api.auth.services.impl;

import com.barbersync.api.features.usuario.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    // --- MÉTODOS PERSONALIZADOS QUE NECESITAMOS PARA @PreAuthorize ---
    public Integer getId() {
        return usuario.getId();
    }

    public String getNombreCompleto() {
        return usuario.getPrimerNombre() + " " + usuario.getPrimerApellido();
    }
    // --------------------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario.getRol() == null || usuario.getRol().getRol() == null) {
            return Collections.emptyList();
        }
        // ¡CRÍTICO! Añade el prefijo ROLE_ que Spring Security espera.
        String rolConPrefijo = "ROLE_" + usuario.getRol().getRol().toUpperCase();
        return Collections.singletonList(new SimpleGrantedAuthority(rolConPrefijo));
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        // Usamos el correo como el "username" para Spring Security
        return usuario.getCorreo();
    }

    // Para simplificar, devolvemos 'true'. Implementa lógica real si necesitas
    // deshabilitar o bloquear cuentas en el futuro.
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}