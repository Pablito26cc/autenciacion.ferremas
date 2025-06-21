package cl.integracion.ms_auth_bff.service;

import cl.integracion.ms_auth_bff.client.AuthDbClient;
import cl.integracion.ms_auth_bff.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthDbClient authDbClient;

    public AuthResponse register(RegisterRequest request) {
        try {
            log.info("Iniciando registro para usuario: {}", request.getUsername());
            
            // Validaciones
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                log.warn("Registro fallido: nombre de usuario vacío");
                return AuthResponse.builder()
                    .success(false)
                    .message("El nombre de usuario es requerido")
                    .build();
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                log.warn("Registro fallido: contraseña vacía");
                return AuthResponse.builder()
                    .success(false)
                    .message("La contraseña es requerida")
                    .build();
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                log.warn("Registro fallido: email vacío");
                return AuthResponse.builder()
                    .success(false)
                    .message("El email es requerido")
                    .build();
            }

            log.info("Verificando si el usuario ya existe: {}", request.getUsername());
            
            // Verificar si el usuario ya existe
            try {
                ResponseEntity<UsuarioResponse> existingUser = authDbClient.findByUsername(request.getUsername());
                log.info("Respuesta de verificación de usuario: {}", existingUser.getStatusCode());
                
                if (existingUser.getStatusCode() == HttpStatus.OK) {
                    log.warn("Registro fallido: usuario ya existe");
                    return AuthResponse.builder()
                        .success(false)
                        .message("El nombre de usuario ya existe")
                        .build();
                }
            } catch (Exception e) {
                log.info("Usuario no encontrado (esperado): {}", e.getMessage());
            }

            log.info("Verificando si el email ya existe: {}", request.getEmail());
            
            // Verificar si el email ya existe
            try {
                ResponseEntity<UsuarioResponse> existingEmail = authDbClient.findByEmail(request.getEmail());
                log.info("Respuesta de verificación de email: {}", existingEmail.getStatusCode());
                
                if (existingEmail.getStatusCode() == HttpStatus.OK) {
                    log.warn("Registro fallido: email ya existe");
                    return AuthResponse.builder()
                        .success(false)
                        .message("El email ya está registrado")
                        .build();
                }
            } catch (Exception e) {
                log.info("Email no encontrado (esperado): {}", e.getMessage());
            }

            // Crear el usuario
            log.info("Creando nuevo usuario: {}", request.getUsername());
            
            ResponseEntity<UsuarioResponse> response = authDbClient.createUsuario(request);
            log.info("Respuesta de creación de usuario: {}", response.getStatusCode());
            
            if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
                UsuarioResponse usuario = response.getBody();
                log.info("Usuario creado exitosamente: {}", usuario);
                
                return AuthResponse.builder()
                    .success(true)
                    .message("Usuario registrado exitosamente")
                    .token(generateToken())
                    .user(AuthResponse.UserInfo.builder()
                        .id(usuario.getId())
                        .username(usuario.getUsername())
                        .email(usuario.getEmail())
                        .build())
                    .build();
            } else {
                log.error("Error en la creación del usuario. Status: {}, Body: {}", 
                         response.getStatusCode(), response.getBody());
                return AuthResponse.builder()
                    .success(false)
                    .message("Error al crear el usuario")
                    .build();
            }

        } catch (Exception e) {
            log.error("Error en el registro: ", e);
            return AuthResponse.builder()
                .success(false)
                .message("Error interno del servidor: " + e.getMessage())
                .build();
        }
    }

    public AuthResponse login(LoginRequest request) {
        try {
            log.info("Iniciando login para usuario: {}", request.getUsername());
            
            // Validaciones
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                log.warn("Login fallido: nombre de usuario vacío");
                return AuthResponse.builder()
                    .success(false)
                    .message("El nombre de usuario es requerido")
                    .build();
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                log.warn("Login fallido: contraseña vacía");
                return AuthResponse.builder()
                    .success(false)
                    .message("La contraseña es requerida")
                    .build();
            }

            // Intentar login
            log.info("Intentando autenticar usuario: {}", request.getUsername());
            
            ResponseEntity<UsuarioResponse> response = authDbClient.login(request);
            log.info("Respuesta de login: {}", response.getStatusCode());
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                UsuarioResponse usuario = response.getBody();
                log.info("Login exitoso para usuario: {}", usuario);
                
                return AuthResponse.builder()
                    .success(true)
                    .message("Login exitoso")
                    .token(generateToken())
                    .user(AuthResponse.UserInfo.builder()
                        .id(usuario.getId())
                        .username(usuario.getUsername())
                        .email(usuario.getEmail())
                        .build())
                    .build();
            } else {
                log.warn("Login fallido. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
                return AuthResponse.builder()
                    .success(false)
                    .message("Credenciales inválidas")
                    .build();
            }

        } catch (Exception e) {
            log.error("Error en el login: ", e);
            return AuthResponse.builder()
                .success(false)
                .message("Error interno del servidor: " + e.getMessage())
                .build();
        }
    }

    private String generateToken() {
        // En un entorno real, aquí se generaría un JWT token
        return UUID.randomUUID().toString();
    }
} 