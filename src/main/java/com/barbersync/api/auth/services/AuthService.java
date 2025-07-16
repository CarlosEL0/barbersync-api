// En AuthService.java
package com.barbersync.api.auth.services;

import com.barbersync.api.auth.dto.*;
import jakarta.validation.Valid;

public interface AuthService {
    RegisterResponseDto registrarUsuario(RegisterRequestDto request);
    // La firma ya no necesita HttpServletRequest
    LoginResponseDto login(LoginRequestDto request);
    AboutMeResponseDto obtenerPerfil(Integer idUsuario);
    //Nuevo metodo para registro de usuarios
    RegisterResponseDto registrarCliente(@Valid ClientRegisterRequestDto request);
}