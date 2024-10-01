package org.taskify.entity;

import javax.persistence.*;

@Entity
@Table(name = "Responsaveis")
public class Responsaveis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsaveis")
    private Integer idResponsaveis;

    @ManyToOne
    @JoinColumn(name = "Usuarios_id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    public Integer getIdResponsaveis() {
        return idResponsaveis;
    }

    public void setIdResponsaveis(Integer idResponsaveis) {
        this.idResponsaveis = idResponsaveis;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
