package com.barbersync.api.features.usuario.services.impl;

import com.barbersync.api.features.horario.Horario;
import com.barbersync.api.features.horario.HorarioRepository;
import com.barbersync.api.features.horario.entities.TiempoSesion;
import com.barbersync.api.features.horario.TiempoSesionRepository;
import com.barbersync.api.features.rol.Rol;
import com.barbersync.api.features.rol.RolRepository;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioMapper;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.features.usuario.dto.UsuarioRequest;
import com.barbersync.api.features.usuario.dto.UsuarioResponse;
import com.barbersync.api.features.usuario.dto.UsuarioUpdateRequest;
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
    private final RolRepository rolRepository;
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

// REEMPLAZA TU MÉTODO ACTUAL CON ESTE CÓDIGO CORREGIDO

    @Override
    @Transactional // Es importante que el método sea transaccional
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request) {
        // 1. Buscamos el usuario existente en la base de datos
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        // 2. Usamos el mapper para actualizar los campos simples (nombre, correo, etc.)
        //    Asegúrate de que tu mapper NO intente actualizar el Rol.
        usuarioMapper.updateEntityFromRequest(usuario, request);

        // 3. Manejamos la actualización del Rol de forma separada y segura
        if (request.getRolId() != null) {
            // Buscamos la entidad Rol completa por su ID
            Rol nuevoRol = rolRepository.findById(request.getRolId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con ID: " + request.getRolId()));
            // Asignamos la instancia completa del Rol a nuestro usuario
            usuario.setRol(nuevoRol);
        }

        // 4. Actualizamos la contraseña solo si se ha proporcionado una nueva
        if (request.getContrasena() != null && !request.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        }

        // 5. Guardamos la entidad Usuario actualizada en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 6. Convertimos la entidad guardada a un DTO de respuesta y la devolvemos
        return usuarioMapper.toResponse(usuarioGuardado);
    }

    @Override
    @Transactional // <-- ¡Esta anotación asegura que todo el methods sea una transacción segura!
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
    // ==========================================================
    // === ✅✅✅ AÑADE ESTE METODO COMPLETO AL FINAL ✅✅✅ ===
    // ==========================================================
    @Override
    @Transactional
    public UsuarioResponse actualizarUrlImagen(Integer id, String urlImagen) {
        // 1. Buscamos al usuario que queremos actualizar
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        // 2. Actualizamos el campo de la URL en la entidad
        // (Esto asume que ya agregaste el campo 'urlImagen' a tu clase Usuario.java)
        usuario.setUrlImagen(urlImagen);

        // 3. Guardamos los cambios en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 4. Convertimos la entidad actualizada a un DTO de respuesta y la devolvemos
        return usuarioMapper.toResponse(usuarioGuardado);
    }

    @Override
    @Transactional
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioUpdateRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

        // Como el nuevo DTO solo tiene campos seguros, podemos mapearlos
        // sin la lógica compleja del Rol y la Contraseña.

        // Vamos a mapearlos manualmente para mayor claridad:
        if (request.getPrimerNombre() != null) {
            usuario.setPrimerNombre(request.getPrimerNombre());
        }
        if (request.getPrimerApellido() != null) {
            usuario.setPrimerApellido(request.getPrimerApellido());
        }
        // Maneja campos opcionales que pueden ser nulos
        usuario.setSegundoNombre(request.getSegundoNombre());
        usuario.setSegundoApellido(request.getSegundoApellido());

        if (request.getCorreo() != null) {
            usuario.setCorreo(request.getCorreo());
        }

        // NO hay lógica de contraseña aquí, por lo que nunca se actualizará por error.

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuarioGuardado);
    }

}
