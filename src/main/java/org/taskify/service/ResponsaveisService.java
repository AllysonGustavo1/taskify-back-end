package org.taskify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskify.entity.Responsaveis;
import org.taskify.entity.Usuario;
import org.taskify.repository.ResponsaveisRepository;
import org.taskify.repository.UsuarioRepository;
import org.taskify.repository.TarefaRepository;

import java.util.List;

@Service
public class ResponsaveisService {

    @Autowired
    private ResponsaveisRepository responsaveisRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Responsaveis> findByUsuarioId(Integer usuarioId) {
        return responsaveisRepository.findByUsuarioId(usuarioId);
    }

    public Responsaveis criarResponsavel(Integer usuarioId, Responsaveis responsavel) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        responsavel.setUsuario(usuario);
        return responsaveisRepository.save(responsavel);
    }

    public void removerResponsavel(Integer id) {
        responsaveisRepository.deleteById(id);
    }

    public boolean nomeJaExiste(Integer usuarioId, String nome) {
        return responsaveisRepository.existsByUsuarioIdAndNome(usuarioId, nome);
    }

    public Responsaveis findByIdResponsaveis(Integer idResponsavel) {
        return responsaveisRepository.findByIdResponsaveis(idResponsavel);
    }

    public Responsaveis findByUsuarioIdAndNome(Integer usuarioId, String nome) {
        return responsaveisRepository.findByUsuarioIdAndNome(usuarioId, nome);
    }

    public boolean responsavelTemTarefas(Integer responsavelId) {
        return tarefaRepository.existsByResponsavelId(responsavelId);
    }
}
