package cl.integracion.ms_auth_db.model.repository;

import cl.integracion.ms_auth_db.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su username
     * @param username el nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Busca un usuario por su email
     * @param email el email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el username especificado
     * @param username el nombre de usuario
     * @return true si existe, false en caso contrario
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el email especificado
     * @param email el email del usuario
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuarios por username que contenga el patrón especificado
     * @param username el patrón de búsqueda
     * @return lista de usuarios que coinciden con el patrón
     */
    List<Usuario> findByUsernameContainingIgnoreCase(String username);

    /**
     * Busca usuarios por email que contenga el patrón especificado
     * @param email el patrón de búsqueda
     * @return lista de usuarios que coinciden con el patrón
     */
    List<Usuario> findByEmailContainingIgnoreCase(String email);
} 