package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import com.barbersync.api.features.usuario.Usuario; // Asegúrate de importar tu clase Usuario

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CitaMapper {

    // METODO PRIVADO PARA CONSTRUIR NOMBRES DE FORMA SEGURA
    private static String construirNombreCompleto(Usuario usuario) {
        if (usuario == null) {
            return "Usuario no asignado";
        }

        List<String> partesNombre = new ArrayList<>();

        if (usuario.getPrimerNombre() != null && !usuario.getPrimerNombre().isBlank()) {
            partesNombre.add(usuario.getPrimerNombre());
        }
        // Puedes agregar el segundo nombre si quieres
        // if (usuario.getSegundoNombre() != null && !usuario.getSegundoNombre().isBlank()) {
        //     partesNombre.add(usuario.getSegundoNombre());
        // }
        if (usuario.getPrimerApellido() != null && !usuario.getPrimerApellido().isBlank()) {
            partesNombre.add(usuario.getPrimerApellido());
        }

        return partesNombre.isEmpty() ? "Nombre no disponible" : String.join(" ", partesNombre);
    }

    public static CitaResponse toResponse(Cita cita) {
        // Un chequeo de seguridad inicial
        if (cita == null) {
            return null;
        }

        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setFecha(cita.getFecha());
        response.setHora(cita.getHora());

        // Asumiendo que getDuracionTotalMinutos no puede ser null en la entidad
        if(cita.getDuracionTotalMinutos() != null) {
            response.setDuracionTotalMinutos(cita.getDuracionTotalMinutos());
        } else {
            response.setDuracionTotalMinutos(0); // Valor por defecto
        }

        // Usamos el método seguro para construir el nombre
        response.setNombreCliente(construirNombreCompleto(cita.getCliente()));
        response.setNombreBarbero(construirNombreCompleto(cita.getBarbero()));

        // Asignamos los IDs si existen
        if (cita.getCliente() != null) {
            response.setIdCliente(cita.getCliente().getId());
        }
        if (cita.getBarbero() != null) {
            response.setIdBarbero(cita.getBarbero().getId());
        }

        // Manejamos el estado de la cita de forma segura
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