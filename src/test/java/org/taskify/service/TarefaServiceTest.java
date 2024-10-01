package org.taskify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.taskify.entity.Tarefa;
import org.taskify.entity.Usuario;
import org.taskify.repository.TarefaRepository;
import org.taskify.repository.ResponsaveisRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private ResponsaveisRepository responsaveisRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        Tarefa tarefa1 = new Tarefa();
        tarefa1.setIdTarefa(1);
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setIdTarefa(2);
        tarefas.add(tarefa1);
        tarefas.add(tarefa2);

        when(tarefaRepository.findAll()).thenReturn(tarefas);

        List<Tarefa> result = tarefaService.getAllTarefas();

        assertEquals(2, result.size());
        verify(tarefaRepository).findAll();
    }

    @Test
    void testGetTarefasByUserId() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setIdTarefa(1);
        tarefa1.setUsuario(usuario);

        List<Tarefa> tarefas = new ArrayList<>();
        tarefas.add(tarefa1);

        when(tarefaRepository.findByUsuarioId(1)).thenReturn(tarefas);

        List<Tarefa> result = tarefaService.getTarefasByUserId(1);

        assertEquals(1, result.size());
        verify(tarefaRepository).findByUsuarioId(1);
    }

    @Test
    void testCreateTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setUsuario(new Usuario());
        tarefa.getUsuario().setId(1);
        tarefa.setTitulo("Nova Tarefa");
        tarefa.setDescricao("Descrição da tarefa");
        tarefa.setPrioridade("Alta");

        when(tarefaRepository.findMaxNumeroByUsuarioId(1)).thenReturn(null);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa result = tarefaService.createTarefa(tarefa);

        assertNotNull(result);
        assertEquals("Nova Tarefa", result.getTitulo());
        verify(tarefaRepository).save(any(Tarefa.class));
    }

    @Test
    void testDeleteTarefaByUserId_ExistingTarefa() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(1);
        tarefa.setUsuario(usuario);

        when(tarefaRepository.findById(1)).thenReturn(Optional.of(tarefa));

        boolean result = tarefaService.deleteTarefaByUserId(1, 1);

        assertTrue(result);
        verify(tarefaRepository).delete(tarefa);
    }

    @Test
    void testDeleteTarefaByUserId_NonExistingTarefa() {
        when(tarefaRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = tarefaService.deleteTarefaByUserId(1, 1);

        assertFalse(result);
        verify(tarefaRepository, never()).delete(any(Tarefa.class));
    }

    @Test
    void testUpdateTarefa_NonExistingTarefa() {
        when(tarefaRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            tarefaService.updateTarefa(1, new Tarefa());
        });

        assertEquals("Tarefa não encontrada", exception.getMessage());
    }

    @Test
    void testUpdateTarefaByUserIdAndId_UserMismatch() {
        Usuario usuario = new Usuario();
        usuario.setId(1);

        Tarefa existingTarefa = new Tarefa();
        existingTarefa.setIdTarefa(1);
        existingTarefa.setUsuario(usuario);

        when(tarefaRepository.findById(1)).thenReturn(Optional.of(existingTarefa));

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            tarefaService.updateTarefaByUserIdAndId(2, 1, new Tarefa());
        });

        assertEquals("Tarefa não pertence ao usuário", exception.getMessage());
    }
}