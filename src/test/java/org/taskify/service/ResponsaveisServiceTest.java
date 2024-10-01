package org.taskify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.taskify.entity.Responsaveis;
import org.taskify.entity.Usuario;
import org.taskify.repository.ResponsaveisRepository;
import org.taskify.repository.UsuarioRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResponsaveisServiceTest {

    @Mock
    private ResponsaveisRepository responsaveisRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ResponsaveisService responsaveisService;

    @Test
    public void testFindByUsuarioId() {
        Integer usuarioId = 1;
        when(responsaveisRepository.findByUsuarioId(usuarioId)).thenReturn(Collections.emptyList());

        assertTrue(responsaveisService.findByUsuarioId(usuarioId).isEmpty());
        verify(responsaveisRepository).findByUsuarioId(usuarioId);
    }

    @Test
    public void testCriarResponsavel() {
        Integer usuarioId = 1;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Responsaveis responsavel = new Responsaveis();
        responsavel.setNome("Responsável");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(responsaveisRepository.save(any(Responsaveis.class))).thenReturn(responsavel);

        Responsaveis savedResponsavel = responsaveisService.criarResponsavel(usuarioId, responsavel);
        assertNotNull(savedResponsavel);
        assertEquals("Responsável", savedResponsavel.getNome());
    }

    @Test
    public void testCriarResponsavel_UserNotFound() {
        Integer usuarioId = 1;
        Responsaveis responsavel = new Responsaveis();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                responsaveisService.criarResponsavel(usuarioId, responsavel));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}
