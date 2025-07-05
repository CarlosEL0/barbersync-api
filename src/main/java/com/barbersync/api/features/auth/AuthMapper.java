package com.barbersync.api.features.auth;

import com.barbersync.api.features.auth.dto.AboutMeResponseDto;
import com.barbersync.api.features.usuario.Usuario;

public class AuthMapper {

    public static AboutMeResponseDto toAboutMeDto(Usuario usuario) {
        return new AboutMeResponseDto(
                usuario.getPrimerNombre(),
                usuario.getPrimerApellido(),
                usuario.getCorreo()
        );
    }
}
