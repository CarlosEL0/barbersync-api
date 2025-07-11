package com.barbersync.api.auth.services;

import com.barbersync.api.auth.dto.*;

public interface AuthService {
    RegisterResponseDto registrarUsuario(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
    AboutMeResponseDto obtenerPerfil(Integer idUsuario); // opcional
}
