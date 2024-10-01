package org.taskify.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.taskify.entity.Responsaveis;
import org.taskify.service.ResponsaveisService;
import org.taskify.service.TarefaService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResponsaveisControllerTest {

    @InjectMocks
    private ResponsaveisController responsaveisController;

    @Mock
    private ResponsaveisService responsaveisService;

    @Mock
    private TarefaService tarefaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetResponsaveisByUsuarioId() {
        when(responsaveisService.findByUsuarioId(1)).thenReturn(Arrays.asList(new Responsaveis()));

        ResponseEntity<List<Responsaveis>> response = responsaveisController.getResponsaveisByUsuarioId(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(responsaveisService).findByUsuarioId(1);
    }

    @Test
    void testCriarResponsavelParaUsuario() {
        Responsaveis responsavel = new Responsaveis();
        responsavel.setNome("Teste");

        when(responsaveisService.nomeJaExiste(1, "Teste")).thenReturn(false);
        when(responsaveisService.criarResponsavel(1, responsavel)).thenReturn(responsavel);

        ResponseEntity<Responsaveis> response = responsaveisController.criarResponsavelParaUsuario(1, responsavel);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responsavel, response.getBody());
    }

    @Test
    void testRemoverResponsavel() {
        when(tarefaService.responsavelTemTarefas(1)).thenReturn(false);
        doNothing().when(responsaveisService).removerResponsavel(1);

        ResponseEntity<String> response = responsaveisController.removerResponsavel(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Responsável removido com sucesso.", response.getBody());
    }

    @Test
    void testVerificarNomeExistente() {
        when(responsaveisService.nomeJaExiste(1, "Teste")).thenReturn(true);

        ResponseEntity<Boolean> response = responsaveisController.verificarNomeExistente(1, "Teste");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
    }

    @Test
    void testGetResponsavelById() {
        Responsaveis responsavel = new Responsaveis();
        responsavel.setNome("Teste");

        when(responsaveisService.findByIdResponsaveis(1)).thenReturn(responsavel);

        ResponseEntity<String> response = responsaveisController.getResponsavelById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Teste", response.getBody());
    }
}