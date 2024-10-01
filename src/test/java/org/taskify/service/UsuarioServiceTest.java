package org.taskify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.taskify.entity.Usuario;
import org.taskify.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    public void testFindAll() {
        usuarioService.findAll();
        verify(usuarioRepository).findAll();
    }

    @Test
    public void testCreateUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario savedUsuario = usuarioService.createUsuario(usuario);
        assertNotNull(savedUsuario);
    }

    @Test
    public void testExistsByUsuario() {
        String username = "user";
        when(usuarioRepository.existsByUsuario(username)).thenReturn(true);

        assertTrue(usuarioService.existsByUsuario(username));
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<Usuario> foundUsuario = usuarioService.findById(id);
        assertTrue(foundUsuario.isPresent());
    }

    @Test
    public void testFindById_NotFound() {
        Integer id = 1;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Usuario> foundUsuario = usuarioService.findById(id);
        assertFalse(foundUsuario.isPresent());
    }
}
