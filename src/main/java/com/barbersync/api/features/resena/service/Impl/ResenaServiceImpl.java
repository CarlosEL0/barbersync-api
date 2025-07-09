package com.barbersync.api.features.resena.service.Impl;

import com.barbersync.api.features.resena.Resena;
import com.barbersync.api.features.resena.ResenaRepository;
import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;
import com.barbersync.api.features.resena.service.ResenaService;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResenaServiceImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;  // Agregado para usar el repositorio de Usuario

    @Override
    public ResenaResponse crear(ResenaRequest request) {
        Resena resena = new Resena();

        // Buscar cliente y barbero por ID
        Usuario cliente = usuarioRepository.findById(request.getIdCliente()).orElse(null); // Asegúrate de tener un repositorio de Usuario
        Usuario barbero = usuarioRepository.findById(request.getIdBarbero()).orElse(null);

        // Asignar los valores a la reseña
        resena.setCalificacion(request.getCalificacion());
        resena.setComentario(request.getComentario());
        resena.setFechaResena(java.time.LocalDate.now());
        resena.setCliente(cliente); // Asignar cliente
        resena.setBarbero(barbero); // Asignar barbero

        resena = resenaRepository.save(resena); // Guardar la reseña en la base de datos

        return mapToResponse(resena); // Retornar la respuesta mapeada
    }

    @Override
    public ResenaResponse obtenerPorId(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Resena no encontrada con ID: " + id));
        return mapToResponse(resena);
    }

    @Override
    public List<ResenaResponse> obtenerTodas() {
        return resenaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Método para actualizar reseña
    @Override
    public ResenaResponse actualizar(Integer id, ResenaRequest request) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Resena no encontrada con ID: " + id));

        resena.setCalificacion(request.getCalificacion());
        resena.setComentario(request.getComentario());
        resena.setFechaResena(java.time.LocalDate.now());  // O usa la fecha enviada si se requiere

        resena = resenaRepository.save(resena);

        return mapToResponse(resena);
    }

    @Override
    public void eliminar(Integer id) {
        resenaRepository.deleteById(id);
    }

    private ResenaResponse mapToResponse(Resena resena) {
        ResenaResponse response = new ResenaResponse();
        response.setId(resena.getId());
        response.setCalificacion(resena.getCalificacion());
        response.setComentario(resena.getComentario());
        response.setFechaResena(resena.getFechaResena());
        return response;
    }
}
