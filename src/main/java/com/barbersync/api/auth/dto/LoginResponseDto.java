// En LoginResponseDto.java
package com.barbersync.api.auth.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {
    private Integer idUsuario;
    private String rol;
    private String nombreCompleto;
    private String mensaje;
    private String token; // <-- ¡NUEVO CAMPO PARA EL TOKEN!
}