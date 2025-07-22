package com.barbersync.api.cloudinary.services.impl;

import com.barbersync.api.cloudinary.services.CloudinaryService;
import com.barbersync.api.shared.exceptions.CloudinaryOperationException; // <-- Crearemos esta excepción
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service // Marca esta clase como un servicio de Spring
@RequiredArgsConstructor // Lombok: crea un constructor con los campos 'final'
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary; // Spring inyecta el Bean que creamos en CloudinaryConfig
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Override
    public String uploadFile(MultipartFile file) {
        validateFile(file);
        try {
            // Sube el archivo y Cloudinary le asigna un 'public_id' automáticamente
            var result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            // Devuelve la URL segura (https) del archivo subido
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new CloudinaryOperationException("Error al subir el archivo a Cloudinary", e);
        }
    }

    @Override
    public void deleteFile(String publicId) {
        if (publicId == null || publicId.trim().isEmpty()) {
            throw new IllegalArgumentException("El publicId no puede ser nulo o vacío");
        }
        try {
            // Destruye (elimina) el archivo en Cloudinary usando su publicId
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new CloudinaryOperationException("Error al eliminar el archivo de Cloudinary", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede ser nulo o vacío.");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("El archivo excede el tamaño máximo permitido de 5MB.");
        }
        // Aquí podrías agregar más validaciones, como el tipo de archivo (MIME type)
    }
}