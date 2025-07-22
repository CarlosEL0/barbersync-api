package com.barbersync.api.features.usuario.services.impl;

import com.barbersync.api.features.horario.Horario;
import com.barbersync.api.features.horario.HorarioRepository;
import com.barbersync.api.features.horario.entities.TiempoSesion;
import com.barbersync.api.features.horario.TiempoSesionRepository;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioMapper;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.usuario.services.UsuarioService;
import com.barbersync.api.features.barbero.repositories.BarberoEspecialidadRepository;
import com.barbersync.api.shared.exceptions.CorreoYaExisteException; // ✅ Importamos la nueva excepción
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    // --- DEPENDENCIAS (NO SE TOCAN) ---
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HorarioRepository horarioRepository;
    private final TiempoSesionRepository tiempoSesionRepository;
    private final BarberoEspecialidadRepository barberoEspecialidadRepository;

    // --- MÉTODO crearUsuario (NO SE TOCA, LÓGICA PERFECTA) ---
    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);
        String hash = passwordEncoder.encode(request.getContrasena());
        usuario.setContrasena(hash);
        usuario = usuarioRepository.save(usuario);

        if (usuario.getRol() != null && "BARBERO".equalsIgnoreCase(usuario.getRol().getRol())) {
            TiempoSesion sesionPorDefecto = tiempoSesionRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Tiempo de sesión por defecto no encontrado"));
            Horario horario = new Horario();
            horario.setBarbero(usuario);
            horario.setHoraEntrada(LocalTime.of(9, 0));
            horario.setHoraSalida(LocalTime.of(18, 0));
            horario.setTiempoSesion(sesionPorDefecto);
            horarioRepository.save(horario);
        }
        return usuarioMapper.toResponse(usuario);
    }

    // --- MÉTODO obtenerUsuarioPorId (NO SE TOCA, LÓGICA PERFECTA) ---
    @Override
    public UsuarioResponse obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponse(usuario);
    }

    // --- MÉTODO obtenerUsuarios (NO SE TOCA, LÓGICA PERFECTA) ---
    @Override
    public List<UsuarioResponse> obtenerUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    // =========================================================================
    // === MÉTODO actualizarUsuario (¡ESTA ES LA VERSIÓN FINAL Y CORREGIDA!) ===
    // =========================================================================
    @Override
    @Transactional
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request) {

        // 1. Buscamos al usuario en la base de datos que queremos actualizar.
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        // 2. ✅ ¡LÓGICA DE VALIDACIÓN DE CORREO "INTELIGENTE"! ✅
        //    Esto soluciona el error 400 del validador @UniqueEmail.
        //    Solo validamos la unicidad si el correo que nos llega es DIFERENTE al que ya tiene el usuario.
        if (request.getCorreo() != null && !request.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
            // Si el correo es nuevo, verificamos si ya existe en la base de datos para OTRO usuario.
            if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
                // Si ya existe, lanzamos nuestra excepción personalizada.
                throw new CorreoYaExisteException("El correo '" + request.getCorreo() + "' ya está en uso.");
            }
            // Si no existe, lo actualizamos en el objeto usuario.
            usuario.setCorreo(request.getCorreo());
        }

        // 3. Actualizamos los demás datos del perfil.
        usuario.setPrimerNombre(request.getPrimerNombre());
        usuario.setSegundoNombre(request.getSegundoNombre());
        usuario.setPrimerApellido(request.getPrimerApellido());
        usuario.setSegundoApellido(request.getSegundoApellido());

        // 4. 🚨 ¡LÓGICA DE SEGURIDAD CRÍTICA PARA LA CONTRASEÑA! 🚨
        //    Revisamos si la contraseña que nos llegó es real o es el "placeholder" del frontend.
        String contrasenaRecibida = request.getContrasena();
        if (contrasenaRecibida != null && !contrasenaRecibida.isBlank() && !contrasenaRecibida.equals("password_falsa_para_validar")) {
            // Solo si es una contraseña nueva y real, la codificamos y la actualizamos.
            usuario.setContrasena(passwordEncoder.encode(contrasenaRecibida));
            System.out.println("LOG: Contraseña actualizada para el usuario ID: " + id);
        } else {
            // Si es el placeholder, no hacemos NADA, y la contraseña original se mantiene segura.
            System.out.println("LOG: Contraseña NO actualizada para el usuario ID: " + id);
        }

        // 5. Guardamos todos los cambios en la base de datos.
        var usuarioGuardado = usuarioRepository.save(usuario);

        // 6. Devolvemos el usuario actualizado al frontend.
        return usuarioMapper.toResponse(usuarioGuardado);
    }

    // --- MÉTODO eliminarUsuario (NO SE TOCA, LÓGICA PERFECTA) ---
    @Override
    @Transactional
    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario a eliminar no encontrado con ID: " + id));

        if (usuario.getRol() != null && usuario.getRol().getId() == 1) {
            barberoEspecialidadRepository.deleteByUsuario(usuario);
        }

        usuarioRepository.delete(usuario);
    }

    // --- MÉTODO obtenerUsuariosPorRol (NO SE TOCA, LÓGICA PERFECTA) ---
    @Override
    public List<UsuarioResponse> obtenerUsuariosPorRol(String rol) {
        List<Usuario> usuarios = usuarioRepository.findByRolRolIgnoreCase(rol);
        return usuarios.stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}