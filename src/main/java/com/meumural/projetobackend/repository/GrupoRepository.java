package com.meumural.projetobackend.repository;

import com.meumural.projetobackend.entity.Grupo;
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
    Grupo retornarGrupoPorId(@Param("grupoId") Integer id);

    @Query("SELECT g FROM Grupo g")
    List<Grupo> retornarGrupos();

    @Modifying
    @Transactional
    @Query("UPDATE Grupo g SET g.status = -1 WHERE g.id = :id")
    void apagarGrupo(@Param("id") int id);
}
