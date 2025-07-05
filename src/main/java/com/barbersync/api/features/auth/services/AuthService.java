package com.barbersync.api.features.auth.services;

import com.barbersync.api.features.auth.dto.*;

public interface AuthService {
    RegisterResponseDto registrarUsuario(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
    AboutMeResponseDto obtenerPerfil(Integer idUsuario); // opcional
}
