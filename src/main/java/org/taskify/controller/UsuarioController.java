package org.taskify.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.taskify.dto.UsuarioDTO;
import org.taskify.entity.Usuario;
import org.taskify.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Api(tags = "Usuários", description = "Operações relacionadas aos usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @ApiOperation(value = "Listar todos os usuários", notes = "Retorna uma lista de todos os usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de usuários retornada com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @ApiOperation(value = "Criar um novo usuário", notes = "Adiciona um novo usuário ao sistema.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na criação do usuário"),
            @ApiResponse(code = 409, message = "Usuário já existe"),
            @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            Usuario createdUsuario = usuarioService.createUsuario(usuario);
            return ResponseEntity.ok(createdUsuario);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/exists/usuario/{username}")
    @ApiOperation(value = "Verificar se o usuário existe", notes = "Verifica se um usuário já está cadastrado.")
    public ResponseEntity<Boolean> existsByUsuario(@PathVariable String username) {
        boolean exists = usuarioService.existsByUsuario(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    @ApiOperation(value = "Verificar se o email existe", notes = "Verifica se um e-mail já está cadastrado.")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = usuarioService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/validate")
    @ApiOperation(value = "Validar credenciais do usuário", notes = "Verifica se o nome de usuário ou e-mail e a senha estão corretos.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credenciais válidas"),
            @ApiResponse(code = 404, message = "Usuário não encontrado"),
            @ApiResponse(code = 403, message = "Senha incorreta")
    })
    public ResponseEntity<String> validateCredentials(@RequestBody Credenciais credenciais) {
        Usuario usuario = usuarioService.findByUsuarioOrEmail(credenciais.getUsernameOrEmail());

        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        if (!passwordEncoder.matches(credenciais.getPassword(), usuario.getSenha())) {
            return ResponseEntity.status(403).body("Senha incorreta.");
        }

        return ResponseEntity.ok("Credenciais válidas.");
    }

    public static class Credenciais {
        private String usernameOrEmail;
        private String password;

        public String getUsernameOrEmail() {
            return usernameOrEmail;
        }

        public void setUsernameOrEmail(String usernameOrEmail) {
            this.usernameOrEmail = usernameOrEmail;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualizar usuário", notes = "Atualiza as informações de um usuário existente.")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Optional<Usuario> usuarioOptional = usuarioService.findById(id);
            if (!usuarioOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Usuario usuarioExistente = usuarioOptional.get();

            if (usuarioAtualizado.getUsuario() != null) {
                usuarioExistente.setUsuario(usuarioAtualizado.getUsuario());
            }

            if (usuarioAtualizado.getEmail() != null) {
                usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            }

            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            }

            if (usuarioAtualizado.getRole() != null) {
                usuarioExistente.setRole(usuarioAtualizado.getRole());
            }

            usuarioService.save(usuarioExistente);
            return ResponseEntity.ok(usuarioExistente);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar o usuário.");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletar usuário", notes = "Deleta um usuário existente.")
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuarioOptional = usuarioService.findById(id);
            if (!usuarioOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            usuarioService.deleteById(id);
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao deletar o usuário.");
        }
    }

    @GetMapping("/username/{usuario}") // Altere aqui
    public ResponseEntity<UsuarioDTO> getUsuarioByUsername(@PathVariable String usuario) {
        Usuario usuarioObj = usuarioService.findByUsuarioOrEmail(usuario); // Mude para findByUsuario
        if (usuarioObj != null) {
            return ResponseEntity.ok(new UsuarioDTO(usuarioObj.getId(), usuarioObj.getUsuario()));
        }
        return ResponseEntity.notFound().build();
    }
}
