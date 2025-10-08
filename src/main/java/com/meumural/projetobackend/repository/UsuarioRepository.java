package com.meumural.projetobackend.repository;

import com.meumural.projetobackend.entity.Postagem;
import com.meumural.projetobackend.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.id = :usuarioId")
    Usuario retornarUsuarioPorId(@Param("usuarioId") Integer usuarioId);
    @Query("SELECT u FROM Usuario u")
    List<Usuario> retornarUsuarios();
    @Query("SELECT u.postagens FROM Usuario u WHERE u.id = :usuarioId")
    List<Postagem> postagensPorUsuarioId(@Param("usuarioId") Integer usuarioId);
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.status = -1 WHERE u.id = :id")
    void apagarUsuario(@Param("id") int usuarioId);

    Optional<Usuario> findByEmail(String email);
}
