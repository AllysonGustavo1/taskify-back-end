package org.taskify.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "Tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Integer idTarefa;

    @ManyToOne
    @JoinColumn(name = "Usuarios_id_usuario", nullable = false)
    @NotNull(message = "O campo usuário deve ser preenchido.")
    private Usuario usuario;

    @NotNull(message = "O título deve ser preenchido.")
    @Size(min = 1, max = 50, message = "O título deve ter entre 1 e 50 caracteres.")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "A descrição deve ser preenchida.")
    @Size(min = 1, max = 300, message = "A descrição deve ter entre 1 e 300 caracteres.")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "A prioridade deve ser informada.")
    @Column(nullable = false)
    private String prioridade;

    private LocalDate deadline;

    @NotNull(message = "A situação deve ser informada.")
    @Column(nullable = false)
    private String situacao;

    @Column(nullable = false)
    private Integer numero;

    @NotNull(message = "O ID do responsável deve ser informado.")
    @Column(name = "Responsaveis_id_responsaveis", nullable = false)
    private Integer responsavelId;

    // Getters e setters

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(Integer responsavelId) {
        this.responsavelId = responsavelId;
    }
}
