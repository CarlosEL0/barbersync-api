package com.barbersync.api.features.barbero.services;

import com.barbersync.api.features.barbero.dto.BarberoEspecialidadRequest;
import com.barbersync.api.features.barbero.dto.BarberoEspecialidadResponse;

public interface IBarberoEspecialidadService {
    void asignarEspecialidades(BarberoEspecialidadRequest request);
    BarberoEspecialidadResponse obtenerPorUsuario(Integer idUsuario);
    void eliminarEspecialidadesDeBarbero(Integer idUsuario);
}
