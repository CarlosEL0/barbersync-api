package com.barbersync.api.features.resena.service.Impl;

import com.barbersync.api.features.resena.*;
import com.barbersync.api.features.resena.dto.ResenaRequest;
import com.barbersync.api.features.resena.dto.ResenaResponse;
import com.barbersync.api.features.resena.service.ResenaService;
import com.barbersync.api.features.usuario.Usuario;
import com.barbersync.api.features.usuario.UsuarioRepository;
import com.barbersync.api.shared.exceptions.RecursoNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResenaServiceImpl implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public ResenaResponse crear(ResenaRequest request) {
        Usuario cliente = usuarioRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        Usuario barbero = usuarioRepository.findById(request.getIdBarbero())
                .orElseThrow(() -> new RecursoNoEncontradoException("Barbero no encontrado"));

        Resena resena = ResenaMapper.toEntity(request);
        resena.setIdCliente(cliente);
        resena.setIdBarbero(barbero);

        resena = resenaRepository.save(resena);

        // 🔥 Recargar entidad completa para evitar NullPointerException
        resena = resenaRepository.findById(resena.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Reseña no encontrada tras guardar"));

        return ResenaMapper.toResponse(resena);
    }



    @Override
    public ResenaResponse obtenerPorId(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reseña no encontrada"));
        return ResenaMapper.toResponse(resena);
    }

    @Override
    public List<ResenaResponse> obtenerTodas() {
        return resenaRepository.findAll().stream()
                .map(ResenaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        if (!resenaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Reseña no encontrada");
        }
        resenaRepository.deleteById(id);
    }
}
