package cl.integracion.ms_auth_db.service;

import cl.integracion.ms_auth_db.model.dto.UsuarioDTO;
import cl.integracion.ms_auth_db.model.entities.Usuario;
import cl.integracion.ms_auth_db.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los usuarios
     * @return lista de todos los usuarios
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID
     * @param id el ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su username
     * @param username el nombre de usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Busca un usuario por su email
     * @param email el email del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Guarda un nuevo usuario
     * @param usuarioDTO los datos del usuario a guardar
     * @return el usuario guardado
     * @throws RuntimeException si el username o email ya existen
     */
    public Usuario save(UsuarioDTO usuarioDTO) {
        // Verificar si el username ya existe
        if (usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
            throw new RuntimeException("El username ya existe: " + usuarioDTO.getUsername());
        }

        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya existe: " + usuarioDTO.getEmail());
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword()); // Por ahora sin encriptación
        usuario.setEmail(usuarioDTO.getEmail());

        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza un usuario existente
     * @param id el ID del usuario a actualizar
     * @param usuarioDTO los nuevos datos del usuario
     * @return Optional con el usuario actualizado si existe
     * @throws RuntimeException si el username o email ya existen en otro usuario
     */
    public Optional<Usuario> update(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();

            // Verificar si el nuevo username ya existe en otro usuario
            if (!usuario.getUsername().equals(usuarioDTO.getUsername()) &&
                usuarioRepository.existsByUsername(usuarioDTO.getUsername())) {
                throw new RuntimeException("El username ya existe: " + usuarioDTO.getUsername());
            }

            // Verificar si el nuevo email ya existe en otro usuario
            if (!usuario.getEmail().equals(usuarioDTO.getEmail()) &&
                usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("El email ya existe: " + usuarioDTO.getEmail());
            }

            // Actualizar campos
            usuario.setUsername(usuarioDTO.getUsername());
            if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
                usuario.setPassword(usuarioDTO.getPassword()); // Por ahora sin encriptación
            }
            usuario.setEmail(usuarioDTO.getEmail());

            return Optional.of(usuarioRepository.save(usuario));
        }

        return Optional.empty();
    }

    /**
     * Elimina un usuario por su ID
     * @param id el ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si no existe
     */
    public boolean deleteById(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Verifica las credenciales de un usuario
     * @param username el nombre de usuario
     * @param password la contraseña sin encriptar
     * @return Optional con el usuario si las credenciales son correctas
     */
    public Optional<Usuario> authenticate(String username, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        
        if (usuario.isPresent() && password.equals(usuario.get().getPassword())) {
            return usuario;
        }
        
        return Optional.empty();
    }

    /**
     * Verifica si existe un usuario con el username especificado
     * @param username el nombre de usuario
     * @return true si existe, false en caso contrario
     */
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    /**
     * Verifica si existe un usuario con el email especificado
     * @param email el email del usuario
     * @return true si existe, false en caso contrario
     */
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
} 