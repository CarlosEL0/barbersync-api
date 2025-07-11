package com.barbersync.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔒 Desactiva CSRF
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll() // 🔓 Permite todo sin login
                );

        return http.build();
    }
}
