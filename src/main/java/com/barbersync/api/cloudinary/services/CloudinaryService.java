package com.barbersync.api.cloudinary.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Define el contrato para las operaciones con Cloudinary.
 */
public interface CloudinaryService {

    /**
     * Sube un archivo a Cloudinary.
     * @param file El archivo a subir.
     * @return La URL pública del archivo subido.
     */
    String uploadFile(MultipartFile file);

    /**
     * Elimina un archivo de Cloudinary usando su public ID.
     * @param publicId El ID público del archivo a eliminar.
     */
    void deleteFile(String publicId);
}