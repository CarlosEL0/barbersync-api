    package com.barbersync.api.features.disponibilidad.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class HorarioDisponibleDTO {
        private LocalTime hora;
        private boolean disponible;
    }