package com.meumural.projetobackend.repository;

import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.entity.Postagem;
import com.meumural.projetobackend.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {

    @Query("SELECT g FROM Grupo g WHERE g.id = :grupoId")
    Grupo retornarGrupoPorId(@Param("grupoId") Integer grupoId);
    @Query("SELECT g FROM Grupo g")
    List<Grupo> retornarGrupos();
    @Query("SELECT g.postagens FROM Grupo g WHERE g.id = :grupoId")
    List<Grupo> postagensPorGrupoId(@Param("grupoId") Integer grupoId);

    @Modifying
    @Transactional
    @Query("UPDATE Grupo g SET g.status = -1 WHERE g.id = :id")
    void apagarGrupo(@Param("id") int grupoId);
}
