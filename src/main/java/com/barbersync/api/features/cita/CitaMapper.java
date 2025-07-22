package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import com.barbersync.api.features.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitaMapper {

    // MÉTODO PRIVADO PARA CONSTRUIR NOMBRES DE FORMA SEGURA
    private static String construirNombreCompleto(Usuario usuario) {
        if (usuario == null) {
            return "Usuario no asignado";
        }

        List<String> partesNombre = new ArrayList<>();

        if (usuario.getPrimerNombre() != null && !usuario.getPrimerNombre().isBlank()) {
            partesNombre.add(usuario.getPrimerNombre());
        }
        if (usuario.getPrimerApellido() != null && !usuario.getPrimerApellido().isBlank()) {
            partesNombre.add(usuario.getPrimerApellido());
        }

        return partesNombre.isEmpty() ? "Nombre no disponible" : String.join(" ", partesNombre);
    }

    public static CitaResponse toResponse(Cita cita) {
        if (cita == null) {
            return null;
        }

        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setFecha(cita.getFecha());
        response.setHora(cita.getHora());

        // --- NUEVO: Combinamos fecha y hora ---
        if (cita.getFecha() != null && cita.getHora() != null) {
            LocalDateTime fechaYHora = LocalDateTime.of(cita.getFecha(), cita.getHora());
            response.setFechaHora(fechaYHora.toString()); // Formato ISO: "2025-07-21T15:00"
        } else {
            response.setFechaHora(null);
        }

        // Duración total
        if (cita.getDuracionTotalMinutos() != null) {
            response.setDuracionTotalMinutos(cita.getDuracionTotalMinutos());
        } else {
            response.setDuracionTotalMinutos(0); // Por defecto
        }

        // Nombres
        response.setNombreCliente(construirNombreCompleto(cita.getCliente()));
        response.setNombreBarbero(construirNombreCompleto(cita.getBarbero()));

        // IDs
        if (cita.getCliente() != null) {
            response.setIdCliente(cita.getCliente().getId());
        }
        if (cita.getBarbero() != null) {
            response.setIdBarbero(cita.getBarbero().getId());
        }

        // Estado
        if (cita.getEstadoCita() != null && cita.getEstadoCita().getNombreEstado() != null) {
            response.setEstado(cita.getEstadoCita().getNombreEstado());
        } else {
            response.setEstado("Estado no definido");
        }

        return response;
    }

    public static Cita toEntity(CitaRequest request) {
        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        return cita;
    }
}
