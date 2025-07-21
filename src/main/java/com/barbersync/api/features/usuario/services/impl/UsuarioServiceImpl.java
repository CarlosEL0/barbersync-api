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
import com.barbersync.api.features.barbero.repositories.BarberoEspecialidadRepository; // ✅ CORRECTO
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

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HorarioRepository horarioRepository;
    private final TiempoSesionRepository tiempoSesionRepository;

    // ✅ NUEVO: Inyección para limpiar especialidades si se elimina un barbero
    private final BarberoEspecialidadRepository barberoEspecialidadRepository;

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = usuarioMapper.toEntity(request);

        // Hashear contraseña antes de guardar
        String hash = passwordEncoder.encode(request.getContrasena());
        usuario.setContrasena(hash);

        // Guardar el usuario
        usuario = usuarioRepository.save(usuario);

        // ===================================================
        // Si el usuario tiene rol BARBERO, asignar horario
        // ===================================================
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

    @Override
    public UsuarioResponse obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> obtenerUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
        usuarioMapper.updateEntityFromRequest(usuario, request);
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional // <-- ¡Esta anotación asegura que todo el método sea una transacción segura!
    public void eliminarUsuario(Integer id) {
        // 1. Buscamos el usuario para tener su información, especialmente su ROL.
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario a eliminar no encontrado con ID: " + id));

        // 2. VERIFICAMOS LAS DEPENDENCIAS Y LIMPIAMOS
        // Verificamos si es un barbero para limpiar sus especialidades.
        if (usuario.getRol() != null && usuario.getRol().getId() == 1) { // Suponiendo que el rol de Barbero es 1
            System.out.println("==> Limpiando dependencias del barbero ID: " + id);

            // ¡LA LÓGICA DE LIMPIEZA!
            // Le decimos al repositorio de especialidades que borre todas las filas relacionadas con este usuario.
            barberoEspecialidadRepository.deleteByUsuario(usuario);

            // Aquí también limpiarías otras tablas como Horario, si fuera necesario.
            // horarioRepository.deleteByBarbero(usuario);
        }

        // Aquí podrías añadir lógica para limpiar las citas de un CLIENTE si lo borras.
        // if (usuario.getRol() != null && usuario.getRol().getId() == 2) {
        //     citaRepository.deleteByCliente(usuario);
        // }

        // 3. PASO FINAL: AHORA SÍ, BORRAMOS AL USUARIO
        // Como ya no hay nada que apunte a él, la base de datos permitirá el borrado.
        System.out.println("==> Procediendo a eliminar el usuario ID: " + id);
        usuarioRepository.delete(usuario);
        System.out.println("==> Usuario eliminado con éxito.");
    }

    @Override
    public List<UsuarioResponse> obtenerUsuariosPorRol(String rol) {
        List<Usuario> usuarios = usuarioRepository.findByRolRolIgnoreCase(rol);
        return usuarios.stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}
