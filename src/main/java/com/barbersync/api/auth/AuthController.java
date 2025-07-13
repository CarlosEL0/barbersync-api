package com.barbersync.api.auth;

import com.barbersync.api.auth.dto.*;
import com.barbersync.api.auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.registrarUsuario(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException e) {
            // Devuelve un JSON válido con mensaje de error
            return ResponseEntity.status(401).body(
                    Map.of("mensaje", e.getMessage())
            );
        }
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<AboutMeResponseDto> getProfile(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.obtenerPerfil(id));
    }
}
