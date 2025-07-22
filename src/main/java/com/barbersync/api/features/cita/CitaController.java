package com.barbersync.api.features.cita;

import com.barbersync.api.features.cita.dto.CitaRequest;
import com.barbersync.api.features.cita.dto.CitaResponse;
import com.barbersync.api.features.cita.services.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    // REGLA: Un cliente puede crear una cita para sí mismo. Un barbero o un admin también podrían crearla.
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'BARBERO', 'ADMIN')")
    public ResponseEntity<CitaResponse> crear(@RequestBody @Valid CitaRequest request) {

        // (Opcional) Se podría añadir una validación extra para que un cliente solo pueda crear citas para su propio ID.
        return ResponseEntity.ok(citaService.crear(request));
    }

    // REGLA: Un admin puede ver cualquier cita. Un barbero/cliente solo puede ver citas que le conciernen.
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @citaSecurityService.estaInvolucradoEnCita(authentication, #id)")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    // REGLA: Solo un admin puede ver TODAS las citas sin filtro. El resto de los endpoints son para vistas filtradas.
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitaResponse>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    // REGLA: Un admin puede actualizar cualquier cita. Un barbero/cliente solo si está involucrado.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @citaSecurityService.estaInvolucradoEnCita(authentication, #id)")
    public ResponseEntity<CitaResponse> actualizar(@PathVariable Integer id, @RequestBody @Valid CitaRequest request) {
        return ResponseEntity.ok(citaService.actualizar(id, request));
    }

    // REGLA: Un admin puede eliminar cualquier cita. Un barbero/cliente solo si está involucrado.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @citaSecurityService.estaInvolucradoEnCita(authentication, #id)")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // REGLA: Un cliente puede ver sus propias citas. Un admin puede ver las citas de cualquier cliente.
    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasRole('ADMIN') or #idCliente == authentication.principal.id") // Asumiendo que el 'id' está en el principal
    public ResponseEntity<List<CitaResponse>> obtenerPorCliente(@PathVariable("idCliente") Integer idCliente) {
        return ResponseEntity.ok(citaService.obtenerPorCliente(idCliente));
    }

    // REGLA: Un barbero puede ver sus propias citas. Un admin puede ver las citas de cualquier barbero.
    @GetMapping("/barbero/{idBarbero}")
    @PreAuthorize("hasRole('ADMIN') or #idBarbero == authentication.principal.id")
    public ResponseEntity<List<CitaResponse>> obtenerPorBarbero(@PathVariable("idBarbero") Integer idBarbero) {
        return ResponseEntity.ok(citaService.obtenerPorBarbero(idBarbero));
    }

    // REGLA: Pública o semi-pública, podría ser para ver disponibilidad general. La dejamos abierta o la restringimos.
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaResponse>> obtenerPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.obtenerPorFecha(fecha));
    }

    // REGLA: Un barbero puede ver su agenda para una fecha. Un admin también.
    @GetMapping("/barbero/{idBarbero}/fecha/{fecha}")
    @PreAuthorize("hasRole('ADMIN') or #idBarbero == authentication.principal.id")
    public ResponseEntity<List<CitaResponse>> obtenerPorBarberoYFecha(
            @PathVariable("idBarbero") Integer idBarbero,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.obtenerPorBarberoYFecha(idBarbero, fecha));
    }

    // REGLA: Un cliente puede ver sus citas por estado. Un admin también.
    @GetMapping("/cliente/{idCliente}/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN') or #idCliente == authentication.principal.id")
    public ResponseEntity<List<CitaResponse>> obtenerPorClienteYEstado(
            @PathVariable("idCliente") Integer idCliente,
            @PathVariable String estado) {
        return ResponseEntity.ok(citaService.obtenerPorClienteYEstado(idCliente, estado));
    }
}