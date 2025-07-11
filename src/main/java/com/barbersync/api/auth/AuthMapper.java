package com.barbersync.api.auth;

import com.barbersync.api.auth.dto.AboutMeResponseDto;
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
