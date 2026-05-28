package com.meumural.projetobackend.repository;

import com.meumural.projetobackend.entity.ArquivoPostagem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoPostagemRepository extends JpaRepository<ArquivoPostagem, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE ArquivoPostagem ap SET ap.status = -1 WHERE ap.id = :id")
    void apagarPostagem(@Param("id") int postagemId);
    @Query("SELECT ap FROM ArquivoPostagem ap WHERE ap.id = :arquivoPostagemId")
    ArquivoPostagem arquivoPostagemPorId(@Param("arquivoPostagemId") Integer arquivoPostagemId);
    @Query("SELECT ap FROM ArquivoPostagem ap WHERE ap.postagem.id = :postagemId")
    List<ArquivoPostagem> listarArquivoPostagemPorPostagemId(@Param("postagemId") Integer postagemId);

    @Query("SELECT ap FROM ArquivoPostagem ap")
    List<ArquivoPostagem> listarArquivoPostagens();
}
