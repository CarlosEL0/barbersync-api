package com.barbersync.api.auth.services.impl;

import com.barbersync.api.auth.AuthMapper;
import com.barbersync.api.auth.AuthRepository;
import com.barbersync.api.auth.dto.*;

import com.barbersync.api.auth.services.AuthService;
import com.barbersync.api.features.usuario.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthRepository authRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDto registrarUsuario(RegisterRequestDto request) {
        Usuario usuario = new Usuario();
        usuario.setPrimerNombre(request.getPrimerNombre());
        usuario.setSegundoNombre(request.getSegundoNombre());
        usuario.setPrimerApellido(request.getPrimerApellido());
        usuario.setSegundoApellido(request.getSegundoApellido());
        usuario.setCorreo(request.getEmail());

        // Hashear la contraseña
        String hash = passwordEncoder.encode(request.getContrasena());
        usuario.setContrasena(hash);

        authRepository.save(usuario);

        return new RegisterResponseDto("Usuario registrado exitosamente");
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        Optional<Usuario> usuarioOpt = authRepository.findByCorreo(request.getEmail());

        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(request.getContrasena(), usuarioOpt.get().getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return new LoginResponseDto("Login exitoso");
    }

    @Override
    public AboutMeResponseDto obtenerPerfil(Integer idUsuario) {
        Usuario usuario = authRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return AuthMapper.toAboutMeDto(usuario);
    }
}
