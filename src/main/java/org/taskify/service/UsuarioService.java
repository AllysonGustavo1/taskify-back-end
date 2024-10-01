package org.taskify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskify.entity.Usuario;
import org.taskify.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean existsByUsuario(String usuario) {
        return usuarioRepository.existsByUsuario(usuario);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario findByUsuarioOrEmail(String usernameOrEmail) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(usernameOrEmail);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt.get();
        }

        Optional<Usuario> emailOpt = usuarioRepository.findByEmail(usernameOrEmail);
        return emailOpt.orElse(null);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
