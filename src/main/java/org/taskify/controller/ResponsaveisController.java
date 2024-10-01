package org.taskify.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskify.entity.Responsaveis;
import org.taskify.service.ResponsaveisService;
import org.taskify.service.TarefaService;

import java.util.List;

@RestController
@RequestMapping("/api/responsaveis")
@Api(tags = "Responsaveis", description = "Operações relacionadas aos responsáveis")
public class ResponsaveisController {

    @Autowired
    private ResponsaveisService responsaveisService;

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Responsaveis>> getResponsaveisByUsuarioId(@PathVariable Integer id) {
        List<Responsaveis> responsaveis = responsaveisService.findByUsuarioId(id);
        return ResponseEntity.ok(responsaveis);
    }

    @PostMapping("/usuario/{id}/criar")
    public ResponseEntity<Responsaveis> criarResponsavelParaUsuario(
            @PathVariable Integer id,
            @RequestBody Responsaveis responsavel) {
        try {
            if (responsaveisService.nomeJaExiste(id, responsavel.getNome())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            Responsaveis novoResponsavel = responsaveisService.criarResponsavel(id, responsavel);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoResponsavel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerResponsavel(@PathVariable Integer id) {
        try {
            if (tarefaService.responsavelTemTarefas(id)) {
                return ResponseEntity.badRequest().body("Não é possível excluir este responsável porque ele está atribuído a uma tarefa.");
            }
            responsaveisService.removerResponsavel(id);
            return ResponseEntity.ok("Responsável removido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao remover responsável.");
        }
    }

    @GetMapping("/usuario/{id}/verificar-nome")
    public ResponseEntity<Boolean> verificarNomeExistente(@PathVariable Integer id, @RequestParam String nome) {
        boolean nomeExiste = responsaveisService.nomeJaExiste(id, nome);
        return ResponseEntity.ok(nomeExiste);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getResponsavelById(@PathVariable Integer id) {
        Responsaveis responsavel = responsaveisService.findByIdResponsaveis(id);
        if (responsavel != null) {
            return ResponseEntity.ok(responsavel.getNome());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsável não encontrado.");
        }
    }

    @GetMapping("/usuario/{usuarioId}/nome/{responsavelNome}")
    public ResponseEntity<Integer> getResponsavelIdByNome(@PathVariable Integer usuarioId, @PathVariable String responsavelNome) {
        Responsaveis responsavel = responsaveisService.findByUsuarioIdAndNome(usuarioId, responsavelNome);
        if (responsavel != null) {
            return ResponseEntity.ok(responsavel.getIdResponsaveis());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
