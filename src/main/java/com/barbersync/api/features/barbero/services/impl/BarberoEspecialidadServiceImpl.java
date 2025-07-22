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
    @Transactional
    public void asignarEspecialidades(BarberoEspecialidadRequest request) {
        System.out.println("==> Iniciando asignarEspecialidades (v5 - Estrategia Definitiva y Segura)");

        // 1. Obtenemos el usuario que vamos a modificar. (Esto ya estaba perfecto)
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + request.getIdUsuario()));

        // 2. Validación de rol. (Perfecto también)
        if (usuario.getRol() == null || usuario.getRol().getId() != 1) { // Asumiendo que 1 es 'BARBERO'
            throw new RolInvalidoException("El usuario no tiene rol de barbero");
        }

        // ==========================================================
        // ✅ ¡ESTA ES LA ESTRATEGIA MÁS ROBUSTA!
        // ==========================================================
        System.out.println("==> Buscando especialidades antiguas para el usuario: " + usuario.getPrimerNombre());

        // 3. PRIMERO: Buscamos la lista exacta de las relaciones que vamos a borrar.
        List<BarberoEspecialidad> asignacionesViejas = barberoEspecialidadRepository.findByUsuario(usuario);

        // 4. SEGUNDO: Si la lista no está vacía, la borramos.
        // Esto es más explícito y seguro para JPA que un 'deleteByUsuario()'.
        if (!asignacionesViejas.isEmpty()) {
            System.out.println("==> Borrando " + asignacionesViejas.size() + " especialidades antiguas.");
            barberoEspecialidadRepository.deleteAll(asignacionesViejas);
        }

        // 5. TERCERO: Creamos y guardamos las nuevas. (Esta lógica ya estaba perfecta).
        if (request.getIdEspecialidades() != null && !request.getIdEspecialidades().isEmpty()) {
            System.out.println("==> Creando " + request.getIdEspecialidades().size() + " nuevas asignaciones...");

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
