package com.barbersync.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class RequestDebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("\n--- 🕵️‍♂️ [INICIO DE FILTRO DE DEPURACIÓN] 🕵️‍♂️ ---");
        System.out.println("URI Petición: " + request.getMethod() + " " + request.getRequestURI());

        // Imprimir todas las cabeceras para ver si llega la cookie
        System.out.println("Cabeceras de la Petición:");
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                System.out.println("  " + headerName + ": " + request.getHeader(headerName))
        );

        // Imprimir el estado de autenticación ANTES de que el resto de filtros de Spring actúen
        var authBefore = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Autenticación ANTES de la cadena: " + (authBefore != null ? authBefore : "NULA"));

        // Continuar con el resto de la cadena de filtros
        filterChain.doFilter(request, response);

        // Imprimir el estado de autenticación DESPUÉS de que el resto de filtros hayan actuado
        var authAfter = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Autenticación DESPUÉS de la cadena: " + (authAfter != null ? authAfter.getName() + " - " + authAfter.getAuthorities() : "NULA"));
        System.out.println("--- 🕵️‍♂️ [FIN DE FILTRO DE DEPURACIÓN] 🕵️‍♂️ ---\n");
    }
}