package org.taskify.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taskify.entity.Tarefa;
import org.taskify.service.TarefaService;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@Api(tags = "Tarefas", description = "Operações relacionadas às tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    @ApiOperation(value = "Obter todas as tarefas", notes = "Retorna uma lista de todas as tarefas cadastradas.")
    public List<Tarefa> getAllTarefas() {
        return tarefaService.getAllTarefas();
    }

    @GetMapping("/usuario/{userId}")
    @ApiOperation(value = "Obter tarefas de um usuário", notes = "Retorna uma lista de tarefas associadas ao usuário especificado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefas obtidas com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<List<Tarefa>> getTarefasByUserId(@PathVariable("userId") Integer userId) {
        List<Tarefa> tarefas = tarefaService.getTarefasByUserId(userId);
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping
    @ApiOperation(value = "Criar uma nova tarefa", notes = "Adiciona uma nova tarefa ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na criação da tarefa"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Tarefa> createTarefa(@RequestBody Tarefa tarefa) {
        if (tarefa.getUsuario() == null || tarefa.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (tarefa.getResponsavelId() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (tarefaService.responsavelExists(tarefa.getResponsavelId())) {
            Tarefa createdTarefa = tarefaService.createTarefa(tarefa);
            return ResponseEntity.ok(createdTarefa);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar uma tarefa", notes = "Atualiza uma tarefa existente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Tarefa não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable("id") Integer id, @RequestBody Tarefa tarefa) {
        Tarefa updatedTarefa = tarefaService.updateTarefa(id, tarefa);
        return ResponseEntity.ok(updatedTarefa);
    }

    @PutMapping("/usuario/{userId}/{id}")
    @ApiOperation(value = "Atualizar uma tarefa pelo ID do usuário e ID da tarefa", notes = "Atualiza uma tarefa existente com base no ID do usuário e ID da tarefa fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Tarefa não encontrada ou não pertence ao usuário"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Tarefa> updateTarefaByUserIdAndId(@PathVariable("userId") Integer userId,
                                                            @PathVariable("id") Integer id,
                                                            @RequestBody Tarefa tarefa) {
        Tarefa updatedTarefa = tarefaService.updateTarefaByUserIdAndId(userId, id, tarefa);
        return ResponseEntity.ok(updatedTarefa);
    }

    @DeleteMapping("/{userId}/{id}")
    @ApiOperation(value = "Remover uma tarefa", notes = "Remove uma tarefa existente com base no ID do usuário e o ID da tarefa fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa removida com sucesso"),
            @ApiResponse(code = 404, message = "Tarefa não encontrada ou não pertence ao usuário"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Map<String, String>> deleteTarefa(@PathVariable("userId") Integer userId, @PathVariable("id") Integer id) {
        boolean isDeleted = tarefaService.deleteTarefaByUserId(userId, id);

        if (isDeleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Tarefa removida com sucesso.");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}/numero/{numero}")
    @ApiOperation(value = "Obter tarefa por ID de usuário e número", notes = "Retorna a tarefa associada ao usuário e número especificados.")
    public ResponseEntity<Tarefa> getTarefaByUsuarioIdAndNumero(@PathVariable Integer usuarioId, @PathVariable Integer numero) {
        Tarefa tarefa = tarefaService.getTarefaByUsuarioIdAndNumero(usuarioId, numero);
        return ResponseEntity.ok(tarefa);
    }

    @GetMapping("/responsavel/{responsavelId}/existe")
    @ApiOperation(value = "Verificar se um responsável está atribuído a alguma tarefa", notes = "Retorna verdadeiro se o responsável está atribuído a pelo menos uma tarefa.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Responsável encontrado"),
            @ApiResponse(code = 404, message = "Responsável não encontrado"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Boolean> verificarResponsavelAtribuido(@PathVariable Integer responsavelId) {
        boolean existe = tarefaService.responsavelTemTarefas(responsavelId);
        return ResponseEntity.ok(existe);
    }

    @PutMapping("/{id}/concluir")
    @ApiOperation(value = "Concluir uma tarefa", notes = "Marca uma tarefa como concluída.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tarefa concluída com sucesso"),
            @ApiResponse(code = 404, message = "Tarefa não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<String> concluirTarefa(@PathVariable Integer id) {
        try {
            Tarefa tarefaConcluida = tarefaService.concluirTarefa(id);
            return ResponseEntity.ok("Tarefa '" + tarefaConcluida.getTitulo() + "' concluída com sucesso");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
