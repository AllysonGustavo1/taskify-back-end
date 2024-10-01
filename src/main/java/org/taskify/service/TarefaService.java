package org.taskify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskify.entity.Tarefa;
import org.taskify.repository.TarefaRepository;
import org.taskify.repository.ResponsaveisRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ResponsaveisRepository responsaveisRepository;

    public List<Tarefa> getAllTarefas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> getTarefasByUserId(Integer userId) {
        return tarefaRepository.findByUsuarioId(userId);
    }

    public Tarefa createTarefa(Tarefa tarefa) {
        Integer numeroMaximo = tarefaRepository.findMaxNumeroByUsuarioId(tarefa.getUsuario().getId());

        if (numeroMaximo == null) {
            tarefa.setNumero(1);
        } else {
            tarefa.setNumero(numeroMaximo + 1);
        }

        return tarefaRepository.save(tarefa);
    }

    public boolean deleteTarefaByUserId(Integer userId, Integer tarefaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId).orElse(null);
        if (tarefa == null || !tarefa.getUsuario().getId().equals(userId)) {
            return false;
        }
        tarefaRepository.delete(tarefa);
        return true;
    }

    public Tarefa updateTarefa(Integer id, Tarefa tarefa) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        tarefaExistente.setTitulo(tarefa.getTitulo());
        tarefaExistente.setDescricao(tarefa.getDescricao());
        tarefaExistente.setPrioridade(tarefa.getPrioridade());
        tarefaExistente.setDeadline(tarefa.getDeadline());
        tarefaExistente.setSituacao(tarefa.getSituacao());
        tarefaExistente.setResponsavelId(tarefa.getResponsavelId());

        return tarefaRepository.save(tarefaExistente);
    }

    public Tarefa updateTarefaByUserIdAndId(Integer userId, Integer id, Tarefa tarefa) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        if (!tarefaExistente.getUsuario().getId().equals(userId)) {
            throw new EntityNotFoundException("Tarefa não pertence ao usuário");
        }
        tarefaExistente.setTitulo(tarefa.getTitulo());
        tarefaExistente.setDescricao(tarefa.getDescricao());
        tarefaExistente.setPrioridade(tarefa.getPrioridade());
        tarefaExistente.setDeadline(tarefa.getDeadline());
        tarefaExistente.setSituacao(tarefa.getSituacao());
        tarefaExistente.setResponsavelId(tarefa.getResponsavelId());

        return tarefaRepository.save(tarefaExistente);
    }

    public boolean responsavelExists(Integer responsavelId) {
        return responsaveisRepository.existsById(responsavelId);
    }

    public boolean responsavelTemTarefas(Integer responsavelId) {
        return tarefaRepository.existsByResponsavelId(responsavelId);
    }

    public Tarefa getTarefaByUsuarioIdAndNumero(Integer usuarioId, Integer numero) {
        return tarefaRepository.findByUsuarioIdAndNumero(usuarioId, numero);
    }
}
