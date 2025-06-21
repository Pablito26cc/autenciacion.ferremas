package cl.integracion.ms_auth_db.controller;

import cl.integracion.ms_auth_db.model.dto.UsuarioDTO;
import cl.integracion.ms_auth_db.model.entities.Usuario;
import cl.integracion.ms_auth_db.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Validación básica
            if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (usuarioDTO.getPassword() == null || usuarioDTO.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Usuario nuevoUsuario = usuarioService.save(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Validación básica
            if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Optional<Usuario> usuarioActualizado = usuarioService.update(id, usuarioDTO);
            return usuarioActualizado.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.findByUsername(username);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO.getUsername() == null || usuarioDTO.getPassword() == null) {
                return ResponseEntity.badRequest().build();
            }

            Optional<Usuario> usuario = usuarioService.authenticate(usuarioDTO.getUsername(), usuarioDTO.getPassword());
            return usuario.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 