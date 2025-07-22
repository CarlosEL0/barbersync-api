package com.barbersync.api.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    // Inyecta el valor de la propiedad 'cloudinary.cloud_name' desde application.properties
    @Value("${cloudinary.cloud_name}")
    private String cloudName;

    // Inyecta el valor de la propiedad 'cloudinary.api_key'
    @Value("${cloudinary.api_key}")
    private String apiKey;

    // Inyecta el valor de la propiedad 'cloudinary.api_secret'
    @Value("${cloudinary.api_secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        // Crea un mapa para guardar la configuración
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        // Crea y devuelve una nueva instancia de Cloudinary con la configuración
        return new Cloudinary(config);
    }
}