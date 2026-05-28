-- ============================================================
-- Script de criação do banco de dados - Meu Mural
-- MySQL 8.0+
-- Gerado a partir das classes Entity do projeto
-- ============================================================

CREATE DATABASE IF NOT EXISTS meumural
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE meumural;

-- ============================================================
-- Tabela: usuario
-- Entity: com.meumural.projetobackend.entity.Usuario
-- ============================================================
CREATE TABLE usuario (
    usuario_id INT NOT NULL AUTO_INCREMENT,
    usuario_nome VARCHAR(255),
    usuario_email VARCHAR(255),
    usuario_senha VARCHAR(255),
    usuario_data_criacao DATETIME,
    usuario_status INT,
    PRIMARY KEY (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabela: grupo
-- Entity: com.meumural.projetobackend.entity.Grupo
-- ============================================================
CREATE TABLE grupo (
    grupo_id INT NOT NULL AUTO_INCREMENT,
    grupo_nome VARCHAR(255),
    grupo_descricao VARCHAR(255),
    grupo_data_criacao DATETIME,
    grupo_data_status INT,
    PRIMARY KEY (grupo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabela: postagem
-- Entity: com.meumural.projetobackend.entity.Postagem
-- ============================================================
CREATE TABLE postagem (
    postagem_id INT NOT NULL AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    grupo_id INT,
    postagem_titulo VARCHAR(150),
    postagem_conteudo TEXT,
    postagem_data_criacao DATETIME,
    postagem_status INT,
    PRIMARY KEY (postagem_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id),
    FOREIGN KEY (grupo_id) REFERENCES grupo(grupo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabela: usuario_grupo
-- Entity: com.meumural.projetobackend.entity.UsuarioGrupo
-- ============================================================
CREATE TABLE usuario_grupo (
    usuario_grupo_id INT NOT NULL AUTO_INCREMENT,
    usuario_grupo_data_entrada DATETIME,
    usuario_grupo_status INT,
    grupo_id INT,
    usuario_id INT,
    PRIMARY KEY (usuario_grupo_id),
    FOREIGN KEY (grupo_id) REFERENCES grupo(grupo_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabela: arquivo_postagem
-- Entity: com.meumural.projetobackend.entity.ArquivoPostagem
-- ============================================================
CREATE TABLE arquivo_postagem (
    arquivo_postagem_id INT NOT NULL AUTO_INCREMENT,
    arquivo_postagem_nome VARCHAR(255),
    arquivo_postagem_tipo VARCHAR(255),
    arquivo_postagem_file LONGBLOB,
    arquivo_postagem_status INT,
    postagem_id INT,
    PRIMARY KEY (arquivo_postagem_id),
    FOREIGN KEY (postagem_id) REFERENCES postagem(postagem_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
