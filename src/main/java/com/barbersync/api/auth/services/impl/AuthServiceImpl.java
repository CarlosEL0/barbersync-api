package com.barbersync.api.auth.services.impl;

import com.barbersync.api.auth.AuthMapper;
import com.barbersync.api.auth.dto.*;
import com.barbersync.api.auth.services.AuthService;
import com.barbersync.api.config.JwtService; // <-- ¡NUEVA IMPORTACIÓN!
import com.barbersync.api.features.rol.Rol;
import com.barbersync.api.features.rol.RolRepository;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService; // <-- ¡NUEVA DEPENDENCIA!

    // Constructor actualizado con JwtService
    public AuthServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // El método de registro no cambia, está perfecto.
    @Override
    @Transactional
    public RegisterResponseDto registrarUsuario(RegisterRequestDto request) {
        if (usuarioRepository.findByCorreo(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + request.getRolId()));
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setPrimerNombre(request.getPrimerNombre());
        nuevoUsuario.setSegundoNombre(request.getSegundoNombre());
        nuevoUsuario.setPrimerApellido(request.getPrimerApellido());
        nuevoUsuario.setSegundoApellido(request.getSegundoApellido());
        nuevoUsuario.setCorreo(request.getEmail());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setFechaRegistro(LocalDate.now());
        usuarioRepository.save(nuevoUsuario);
        return new RegisterResponseDto("Usuario registrado exitosamente");
    }

    // --- ¡MÉTODO DE LOGIN REFACTORIZADO PARA DEVOLVER JWT! ---
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        try {
            // 1. Validamos las credenciales del usuario. Si son incorrectas, esto lanzará una excepción.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getContrasena())
            );

            // 2. Si la autenticación es exitosa, buscamos al usuario para obtener sus detalles.
            var usuario = usuarioRepository.findByCorreo(loginRequest.getEmail())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado después de la autenticación."));

            // 3. Creamos un objeto UserDetails para pasárselo al servicio de JWT.
            var userDetails = new CustomUserDetails(usuario);

            // 4. Generamos el token JWT.
            String jwtToken = jwtService.generateToken(userDetails);

            System.out.println("✅ Login exitoso. Token JWT generado para: " + userDetails.getUsername());

            // 5. Devolvemos una respuesta que incluye el token.
            return new LoginResponseDto(
                    usuario.getId(),
                    usuario.getRol().getRol().toLowerCase(),
                    usuario.getPrimerNombre() + " " + usuario.getPrimerApellido(),
                    "Inicio de sesión exitoso",
                    jwtToken // Enviamos el token al frontend
            );

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }
    }

    @Override
    public AboutMeResponseDto obtenerPerfil(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return AuthMapper.toAboutMeDto(usuario);
    }
}