package com.meumural.projetobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.entity.UsuarioGrupo;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Integer> {
    @Query("SELECT ug.grupo FROM UsuarioGrupo ug WHERE ug.usuario.id=:usuarioId")
    List<Grupo> retornarGruposPorUsuarioId(@Param("usuarioId") Integer usuarioId);
    @Query("SELECT ug.usuario FROM UsuarioGrupo ug WHERE ug.grupo.id=:grupoId")
    List<Usuario> retornaUsuariosPorGrupoId(@Param("grupoId") Integer grupoId);
}
