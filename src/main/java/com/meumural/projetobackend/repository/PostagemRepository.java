package com.meumural.projetobackend.repository;

import com.meumural.projetobackend.entity.Postagem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Postagem p SET p.status = -1 WHERE p.id = :id")
    void apagarPostagem(@Param("id") int postagemId);

    @Query("SELECT p FROM Postagem p WHERE p.usuario.id = :usuarioId")
    List<Postagem> listarPostagemPorUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT p FROM Postagem p WHERE p.usuario.id = :usuarioId AND p.status = 1")
    List<Postagem> listarPostagemConcluidasPorUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT p FROM Postagem p WHERE p.usuario.id = :usuarioId AND p.status = 0")
    List<Postagem> listarPostagemNaoConcluidasPorUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT p FROM Postagem p WHERE p.id = :postagemId AND p.usuario.id = :usuarioId")
    Postagem postagemPorUsuarioId(@Param("postagemId") Integer postagemId, @Param("usuarioId") Integer usuarioId);

    @Query("SELECT p FROM Postagem p WHERE p.grupo.id = :grupoId")
    List<Postagem> listarPostagemPorTurmaId(@Param("grupoId") Integer grupoId);

    @Query("SELECT p FROM Postagem p WHERE p.grupo.id = :grupoId AND p.status = 1")
    List<Postagem> listarPostagemConcluidasPorTurmaId(@Param("grupoId") Integer grupoId);

    @Query("SELECT p FROM Postagem p WHERE p.grupo.id = :grupoId AND p.status = 0")
    List<Postagem> listarPostagemNaoConcluidasPorTurmaId(@Param("grupoId") Integer grupoId);

    @Query("SELECT p FROM Postagem p WHERE p.id = :postagemId AND p.grupo.id = :grupoId")
    Postagem postagemPorTurmaId(@Param("postagemId") Integer postagemId, @Param("grupoId") Integer grupoId);

    @Query("SELECT p FROM Postagem p")
    List<Postagem> listarPostagem();

    @Query("SELECT p FROM Postagem p WHERE p.status = 1")
    List<Postagem> listarPostagemConcluidas();

    @Query("SELECT p FROM Postagem p WHERE p.status = 0")
    List<Postagem> listarPostagemNaoConcluida();

    @Query("SELECT p FROM Postagem p WHERE p.id = :postagemId")
    Postagem postagemPorId(@Param("postagemId") Integer postagemId);
}
