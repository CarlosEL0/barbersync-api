package com.barbersync.api.auth;

import com.barbersync.api.auth.dto.*;
import com.barbersync.api.auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // El registro sigue siendo público y no cambia.
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.registrarUsuario(request));
    }

    // --- ¡MÉTODO LOGIN CORREGIDO! ---
    // Ya no necesita el HttpServletRequest porque la autenticación es stateless.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            // Llamamos al método de login que ahora solo necesita los datos del DTO.
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (RuntimeException e) {
            // El manejo de errores sigue siendo el mismo.
            return ResponseEntity.status(401).body(
                    Map.of("mensaje", e.getMessage())
            );
        }
    }

    // El endpoint de perfil no cambia. La autorización se manejará a través del token JWT.
    @GetMapping("/me/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<AboutMeResponseDto> getProfile(@PathVariable Integer id) {
        return ResponseEntity.ok(authService.obtenerPerfil(id));
    }
}