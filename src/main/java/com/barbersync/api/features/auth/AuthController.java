package com.barbersync.api.features.auth;

import com.barbersync.api.features.auth.dto.*;
import com.barbersync.api.features.auth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.registrarUsuario(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<AboutMeResponseDto> getProfile(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.obtenerPerfil(id));
    }
}
