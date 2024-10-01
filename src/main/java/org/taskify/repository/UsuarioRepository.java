package org.taskify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskify.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsuarioOrEmail(String usuario, String email);
    boolean existsByUsuario(String usuario);
    boolean existsByEmail(String email);
}