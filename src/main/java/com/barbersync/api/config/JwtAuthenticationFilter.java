package com.barbersync.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("\n--- 🕵️‍♂️ [JwtAuthenticationFilter] Interceptando Petición: " + request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("--- ❌ Cabecera 'Authorization' no encontrada o no es 'Bearer'. Petición continúa sin autenticar.");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println("--- 🔑 Token JWT extraído: " + jwt);

        try {
            userEmail = jwtService.extractUsername(jwt);
            System.out.println("--- 📧 Email extraído del token: " + userEmail);
        } catch (Exception e) {
            System.out.println("--- 🚨 ERROR: No se pudo extraer el email del token. " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado.");
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("--- 👤 Cargando detalles del usuario para: " + userEmail);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            boolean tokenValido = jwtService.isTokenValid(jwt, userDetails);
            System.out.println("--- ✅ ¿Token es válido? " + tokenValido);

            if (tokenValido) {
                System.out.println("--- ✨ ¡ÉXITO! Creando contexto de autenticación.");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("--- ❌ El token no es válido para el usuario encontrado.");
            }
        } else {
            System.out.println("--- ➡️ El usuario ya estaba autenticado o email es nulo.");
        }

        filterChain.doFilter(request, response);
    }
}
