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
import org.springframework.security.core.context.SecurityContextHolder;
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
    private static final String ROL_CLIENTE_NOMBRE = "CLIENTE";

    // Constructor actualizado con JwtService
    public AuthServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // El metodo de registro no cambia, está perfecto.
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

    // --- ¡NUEVO MÉTODO SEGURO PARA REGISTRAR CLIENTES! ---
    @Override
    @Transactional
    public RegisterResponseDto registrarCliente(ClientRegisterRequestDto request) {
        if (usuarioRepository.findByCorreo(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }

        // 1. Buscamos el rol "CLIENTE" directamente en la base de datos.
        // El rol NO viene de la petición del usuario.
        Rol rolCliente = rolRepository.findByRol(ROL_CLIENTE_NOMBRE) // Necesitarás este método en RolRepository (ver Paso 4)
                .orElseThrow(() -> new IllegalStateException("Configuración del sistema: El rol por defecto 'CLIENTE' no se encuentra en la base de datos."));

        // 2. Creamos el nuevo usuario con los datos del DTO específico para clientes
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setPrimerNombre(request.getPrimerNombre());
        nuevoUsuario.setSegundoNombre(request.getSegundoNombre());
        nuevoUsuario.setPrimerApellido(request.getPrimerApellido());
        nuevoUsuario.setSegundoApellido(request.getSegundoApellido());
        nuevoUsuario.setCorreo(request.getEmail());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        // 3. Asignamos el rol de CLIENTE obtenido de forma segura desde el backend.
        nuevoUsuario.setRol(rolCliente);

        nuevoUsuario.setFechaRegistro(LocalDate.now());

        usuarioRepository.save(nuevoUsuario);

        return new RegisterResponseDto("Cliente registrado exitosamente");
    }


    @Override
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        try {
            // 1. Validamos las credenciales y obtenemos el objeto de autenticación completo.
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // 2. === ¡EL PASO CLAVE QUE FALTABA! ===
            //    Establecemos esta autenticación en el contexto de seguridad de Spring.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Ahora que la autenticación fue exitosa, buscamos al usuario en la BD.
            //    Esto es seguro porque el authenticationManager ya confirmó que existe.
            //    Lo necesitamos para obtener el objeto 'Usuario' completo y sus detalles.
            var usuario = usuarioRepository.findByCorreo(loginRequest.getUsername())
                    .orElseThrow(() -> new IllegalStateException("Usuario autenticado pero no encontrado en la base de datos."));

            // 4. Creamos el objeto UserDetails como lo hacías antes.
            var userDetails = new CustomUserDetails(usuario);

            // 5. Generamos el token JWT.
            String jwtToken = jwtService.generateToken(userDetails);

            System.out.println("✅ Login exitoso. Autenticación establecida y token generado para: " + userDetails.getUsername());

            // 6. Devolvemos la respuesta con el token.
            return new LoginResponseDto(
                    usuario.getId(),
                    usuario.getRol().getRol().toLowerCase(),
                    usuario.getPrimerNombre() + " " + usuario.getPrimerApellido(),
                    "Inicio de sesión exitoso",
                    jwtToken
            );

        } catch (BadCredentialsException e) {
            // Mantenemos el manejo de credenciales incorrectas.
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