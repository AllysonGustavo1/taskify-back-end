package org.taskify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taskify.entity.Responsaveis;

import java.util.List;

@Repository
public interface ResponsaveisRepository extends JpaRepository<Responsaveis, Integer> {

    List<Responsaveis> findByUsuarioId(Integer idUsuario);

    boolean existsByUsuarioIdAndNome(Integer usuarioId, String nome);

    Responsaveis findByIdResponsaveis(Integer idResponsavel);

    Responsaveis findByUsuarioIdAndNome(Integer usuarioId, String nome);
}
