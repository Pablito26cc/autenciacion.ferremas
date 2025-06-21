package cl.integracion.ms_auth_bff.client;

import cl.integracion.ms_auth_bff.model.dto.LoginRequest;
import cl.integracion.ms_auth_bff.model.dto.RegisterRequest;
import cl.integracion.ms_auth_bff.model.dto.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-auth-db", url = "http://localhost:8080")
public interface AuthDbClient {

    @PostMapping("/api/usuarios")
    ResponseEntity<UsuarioResponse> createUsuario(@RequestBody RegisterRequest request);

    @PostMapping("/api/usuarios/login")
    ResponseEntity<UsuarioResponse> login(@RequestBody LoginRequest request);

    @GetMapping("/api/usuarios/username/{username}")
    ResponseEntity<UsuarioResponse> findByUsername(@PathVariable String username);

    @GetMapping("/api/usuarios/email/{email}")
    ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email);
} 