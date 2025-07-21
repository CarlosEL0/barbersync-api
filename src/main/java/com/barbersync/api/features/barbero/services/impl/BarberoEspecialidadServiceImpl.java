package com.barbersync.api.features.barbero.services.impl;

import com.barbersync.api.features.barbero.repositories.BarberoEspecialidadRepository;
import com.barbersync.api.features.barbero.repositories.EspecialidadRepository;
import com.barbersync.api.features.barbero.dto.*;
import com.barbersync.api.features.barbero.entities.*;
import com.barbersync.api.features.barbero.services.IBarberoEspecialidadService;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import com.barbersync.api.shared.exceptions.RolInvalidoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BarberoEspecialidadServiceImpl implements IBarberoEspecialidadService {

    private final BarberoEspecialidadRepository barberoEspecialidadRepository;
    private final EspecialidadRepository especialidadRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void asignarEspecialidades(BarberoEspecialidadRequest request) {
        System.out.println("==> Iniciando asignarEspecialidades (v2 - Corregida)");

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        // ... (tus validaciones de rol, que están perfectas, se mantienen) ...
        if (usuario.getRol() == null || usuario.getRol().getId() != 1) {
            throw new RolInvalidoException("El usuario no tiene rol de barbero");
        }

        // ==========================================================
        // ✅ ¡ESTA ES LA LÓGICA CORREGIDA!
        // ==========================================================
        System.out.println("==> Borrando especialidades antiguas para el usuario: " + usuario.getPrimerNombre());

        // 1. PRIMERO, borramos todas las asignaciones existentes para este barbero.
        barberoEspecialidadRepository.deleteByUsuario(usuario);

        // 2. SEGUNDO, solo si la nueva lista de IDs no está vacía, procedemos a insertar las nuevas.
        if (request.getIdEspecialidades() != null && !request.getIdEspecialidades().isEmpty()) {
            System.out.println("==> Creando nuevas asignaciones...");

            List<BarberoEspecialidad> nuevasAsignaciones = request.getIdEspecialidades().stream()
                    .map(idEsp -> {
                        Especialidad esp = especialidadRepository.findById(idEsp)
                                .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con ID: " + idEsp));

                        BarberoEspecialidad be = new BarberoEspecialidad();
                        be.setUsuario(usuario);
                        be.setEspecialidad(esp);
                        return be;
                    }).collect(Collectors.toList());

            barberoEspecialidadRepository.saveAll(nuevasAsignaciones);
            System.out.println("==> Guardado exitoso de " + nuevasAsignaciones.size() + " nuevas especialidades.");
        } else {
            System.out.println("==> La lista de nuevas especialidades está vacía. No se inserta nada.");
        }
    }


    @Override
    public BarberoEspecialidadResponse obtenerPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        List<BarberoEspecialidad> lista = barberoEspecialidadRepository.findByUsuario(usuario);

        List<String> especialidades = lista.stream()
                .map(be -> be.getEspecialidad().getEspecialidad())
                .collect(Collectors.toList());

        BarberoEspecialidadResponse response = new BarberoEspecialidadResponse();
        response.setIdUsuario(usuario.getId());
        response.setCorreo(usuario.getCorreo());

        String nombreCompleto = usuario.getPrimerNombre() + " " +
                (usuario.getSegundoNombre() != null ? usuario.getSegundoNombre() + " " : "") +
                usuario.getPrimerApellido() + " " +
                (usuario.getSegundoApellido() != null ? usuario.getSegundoApellido() : "");
        response.setNombreCompleto(nombreCompleto.trim());
        response.setEspecialidades(especialidades);

        return response;
    }

    @Override
    public void eliminarEspecialidadesDeBarbero(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        List<BarberoEspecialidad> existentes = barberoEspecialidadRepository.findByUsuario(usuario);
        barberoEspecialidadRepository.deleteAll(existentes);
    }
}
