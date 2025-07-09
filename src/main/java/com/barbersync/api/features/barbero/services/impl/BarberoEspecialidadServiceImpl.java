package com.barbersync.api.features.barbero.services.impl;

import com.barbersync.api.features.barbero.BarberoEspecialidadRepository;
import com.barbersync.api.features.barbero.EspecialidadRepository;
import com.barbersync.api.features.barbero.dto.*;
import com.barbersync.api.features.barbero.entities.*;
import com.barbersync.api.features.barbero.services.BarberoEspecialidadService;
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
public class BarberoEspecialidadServiceImpl implements BarberoEspecialidadService {

    private final BarberoEspecialidadRepository barberoEspecialidadRepository;
    private final EspecialidadRepository especialidadRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void asignarEspecialidades(BarberoEspecialidadRequest request) {
        System.out.println("==> Iniciando asignarEspecialidades");

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        System.out.println("==> Usuario encontrado: " + usuario.getPrimerNombre() + ", Rol: " +
                (usuario.getRol() != null ? usuario.getRol().getRol() : "ROL NULO"));

        if (usuario.getRol() == null) {
            throw new RolInvalidoException("El usuario no tiene un rol asignado");
        }

        if (usuario.getRol().getId() != 1) {
            throw new RolInvalidoException("El usuario no tiene rol de barbero");
        }

        System.out.println("==> Usuario validado como barbero, procesando especialidades...");

        List<BarberoEspecialidad> lista = request.getIdEspecialidades().stream()
                .map(idEsp -> {
                    System.out.println("==> Procesando especialidad ID: " + idEsp);
                    Especialidad esp = especialidadRepository.findById(idEsp)
                            .orElseThrow(() -> {
                                System.out.println("❌ Especialidad NO encontrada: " + idEsp);
                                return new RecursoNoEncontradoException("Especialidad no encontrada con ID: " + idEsp);
                            });
                    System.out.println("✅ Especialidad encontrada: " + esp.getEspecialidad());

                    BarberoEspecialidad be = new BarberoEspecialidad();
                    be.setUsuario(usuario);
                    be.setEspecialidad(esp);
                    return be;
                }).collect(Collectors.toList());


        System.out.println("==> Especialidades construidas: " + lista.size());

        barberoEspecialidadRepository.saveAll(lista);

        System.out.println("==> Guardado exitoso.");

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
