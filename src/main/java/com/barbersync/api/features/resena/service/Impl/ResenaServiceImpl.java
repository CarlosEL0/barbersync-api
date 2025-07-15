// Archivo: ResenaServiceImpl.java (Implementación Completa)
package com.barbersync.api.features.resena.service.Impl;

import com.barbersync.api.features.resena.Resena;
import com.barbersync.api.features.resena.ResenaMapper;
import com.barbersync.api.features.resena.ResenaRepository;
import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;
import com.barbersync.api.features.resena.service.ResenaService;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException; // Asegúrate de que la ruta de tu excepción sea correcta
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Esta anotación de Lombok crea el constructor con los campos 'final'
public class ResenaServiceImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository; // Inyectado gracias a @RequiredArgsConstructor

    @Override
    @Transactional
    public ResenaResponse crear(ResenaRequest request) {
        // 1. Buscar al cliente por su ID desde el request
        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + request.getIdCliente()));

        // 2. Buscar al barbero por su ID desde el request
        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado con ID: " + request.getIdBarbero()));

        // 3. Convertir el request a una entidad Resena (sin las relaciones)
        Resena nuevaResena = ResenaMapper.toEntity(request);

        // 4. ASIGNAR LAS ENTIDADES COMPLETAS (Este es el paso clave que faltaba)
        nuevaResena.setCliente(cliente);
        nuevaResena.setBarbero(barbero);

        // 5. Guardar la entidad Resena, que ahora está completa
        Resena resenaGuardada = resenaRepository.save(nuevaResena);

        // 6. Convertir la entidad guardada (con cliente y barbero) a un DTO de respuesta
        return ResenaMapper.toResponse(resenaGuardada);
    }

    @Override
    @Transactional
    public ResenaResponse actualizar(Integer id, ResenaRequest request) {
        // 1. Buscar la reseña que se quiere actualizar
        Resena resenaExistente = resenaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Resena no encontrada para actualizar con ID: " + id));

        // 2. Buscar las (posiblemente nuevas) entidades de cliente y barbero
        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + request.getIdCliente()));

        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado con ID: " + request.getIdBarbero()));

        // 3. Actualizar los campos de la entidad que ya existe en la BD
        resenaExistente.setCalificacion(request.getCalificacion());
        resenaExistente.setComentario(request.getComentario());
        resenaExistente.setCliente(cliente);
        resenaExistente.setBarbero(barbero);

        // 4. Spring Data JPA detectará los cambios y los guardará al final de la transacción.
        //    resenaRepository.save() es explícito y también funciona.

        // 5. Devolver la respuesta mapeada desde la entidad actualizada
        return ResenaMapper.toResponse(resenaExistente);
    }

    @Override
    @Transactional(readOnly = true)
    public ResenaResponse obtenerPorId(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Resena no encontrada con ID: " + id));
        return ResenaMapper.toResponse(resena);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResenaResponse> obtenerTodas() {
        return resenaRepository.findAll().stream()
                .map(ResenaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!resenaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Resena no encontrada para eliminar con ID: " + id);
        }
        resenaRepository.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ResenaResponse> obtenerPorBarbero(Integer idBarbero) {
        Usuario barbero = usuarioRepository.findById(idBarbero)
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado con ID: " + idBarbero));

        List<Resena> resenas = resenaRepository.findByBarbero(barbero);

        return resenas.stream()
                .map(ResenaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResenaResponse> obtenerPorCliente(Integer idCliente) {
        Usuario cliente = usuarioRepository.findById(idCliente)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + idCliente));

        List<Resena> resenas = resenaRepository.findByCliente(cliente);

        return resenas.stream()
                .map(ResenaMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public Double obtenerPromedioCalificacion(Integer idBarbero) {
        if (!usuarioRepository.existsById(idBarbero)) {
            throw new RecursoNoEncontradoException("Barbero no encontrado con ID: " + idBarbero);
        }

        return resenaRepository.obtenerPromedioCalificacionPorBarbero(idBarbero);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ResenaResponse> obtenerPorBarberoEntreFechas(Integer idBarbero, LocalDate inicio, LocalDate fin) {
        Usuario barbero = usuarioRepository.findById(idBarbero)
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado con ID: " + idBarbero));

        List<Resena> resenas = resenaRepository.findByBarberoAndFechaResenaBetween(barbero, inicio, fin);

        return resenas.stream()
                .map(ResenaMapper::toResponse)
                .collect(Collectors.toList());
    }

}