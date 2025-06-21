package cl.integracion.ms_auth_bff.controller;

import cl.integracion.ms_auth_bff.model.dto.LoginRequest;
import cl.integracion.ms_auth_bff.model.dto.RegisterRequest;
import cl.integracion.ms_auth_bff.model.dto.AuthResponse;
import cl.integracion.ms_auth_bff.model.dto.UsuarioResponse;
import cl.integracion.ms_auth_bff.service.AuthService;
import cl.integracion.ms_auth_bff.client.AuthDbClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AuthDbClient authDbClient;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en el controlador de registro: ", e);
            return ResponseEntity.badRequest().body(
                AuthResponse.builder()
                    .success(false)
                    .message("Error en el registro: " + e.getMessage())
                    .build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            log.error("Error en el controlador de login: ", e);
            return ResponseEntity.badRequest().body(
                AuthResponse.builder()
                    .success(false)
                    .message("Error en el login: " + e.getMessage())
                    .build()
            );
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("BFF Auth Service is running!");
    }

    @GetMapping("/test-connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        try {
            log.info("Probando conexión con microservicio de base de datos...");
            
            // Intentar hacer una llamada simple al microservicio
            ResponseEntity<UsuarioResponse> response = authDbClient.findByUsername("test");
            
            log.info("Respuesta del microservicio: {}", response.getStatusCode());
            
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Conexión exitosa con microservicio",
                "microserviceStatus", response.getStatusCode().toString()
            ));
            
        } catch (Exception e) {
            log.error("Error al conectar con microservicio: ", e);
            return ResponseEntity.status(503).body(Map.of(
                "status", "error",
                "message", "Error de conexión con microservicio: " + e.getMessage()
            ));
        }
    }
} 