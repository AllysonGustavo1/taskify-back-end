package org.taskify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.taskify.entity.Tarefa;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    boolean existsByResponsavelId(Integer responsavelId);

    @Query("SELECT MAX(t.numero) FROM Tarefa t WHERE t.usuario.id = :usuarioId")
    Integer findMaxNumeroByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId")
    List<Tarefa> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT t FROM Tarefa t WHERE t.usuario.id = :usuarioId AND t.numero = :numero")
    Tarefa findByUsuarioIdAndNumero(@Param("usuarioId") Integer usuarioId, @Param("numero") Integer numero);
}
