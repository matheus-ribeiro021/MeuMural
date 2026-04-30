-- ============================================================
-- Script de criação do banco de dados - Meu Mural
-- MySQL 8.0+
-- ============================================================

CREATE DATABASE IF NOT EXISTS meumural
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE meumural;

-- ------------------------------------------------------------
-- Tabela: usuario
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
    usuario_id      INT             NOT NULL AUTO_INCREMENT,
    usuario_nome    VARCHAR(150)    NOT NULL,
    usuario_email   VARCHAR(200)    NOT NULL UNIQUE,
    usuario_senha   VARCHAR(255)    NOT NULL,
    usuario_data_criacao DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_status  TINYINT         NOT NULL DEFAULT 1,

    CONSTRAINT pk_usuario PRIMARY KEY (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabela: grupo
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS grupo (
    grupo_id            INT             NOT NULL AUTO_INCREMENT,
    grupo_nome          VARCHAR(150)    NOT NULL,
    grupo_descricao     VARCHAR(500),
    grupo_data_criacao  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    grupo_data_status   TINYINT         NOT NULL DEFAULT 1,

    CONSTRAINT pk_grupo PRIMARY KEY (grupo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabela: postagem
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS postagem (
    postagem_id             INT             NOT NULL AUTO_INCREMENT,
    usuario_id              INT             NOT NULL,
    grupo_id                INT,
    postagem_titulo         VARCHAR(150),
    postagem_conteudo       TEXT,
    postagem_data_criacao   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    postagem_status         TINYINT         NOT NULL DEFAULT 1,

    CONSTRAINT pk_postagem      PRIMARY KEY (postagem_id),
    CONSTRAINT fk_postagem_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_postagem_grupo
        FOREIGN KEY (grupo_id) REFERENCES grupo(grupo_id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabela: usuario_grupo  (relacionamento N:N entre usuario e grupo)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario_grupo (
    usuario_grupo_id            INT         NOT NULL AUTO_INCREMENT,
    usuario_id                  INT         NOT NULL,
    grupo_id                    INT         NOT NULL,
    usuario_grupo_data_entrada  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_grupo_status        TINYINT     NOT NULL DEFAULT 1,

    CONSTRAINT pk_usuario_grupo PRIMARY KEY (usuario_grupo_id),
    CONSTRAINT fk_ug_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_ug_grupo
        FOREIGN KEY (grupo_id) REFERENCES grupo(grupo_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_usuario_grupo UNIQUE (usuario_id, grupo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ------------------------------------------------------------
-- Tabela: arquivo_postagem
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS arquivo_postagem (
    arquivo_postagem_id     INT             NOT NULL AUTO_INCREMENT,
    postagem_id             INT             NOT NULL,
    arquivo_postagem_nome   VARCHAR(255),
    arquivo_postagem_tipo   VARCHAR(100),
    arquivo_postagem_file   LONGBLOB,
    arquivo_postagem_status TINYINT         NOT NULL DEFAULT 1,

    CONSTRAINT pk_arquivo_postagem PRIMARY KEY (arquivo_postagem_id),
    CONSTRAINT fk_arquivo_postagem
        FOREIGN KEY (postagem_id) REFERENCES postagem(postagem_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Índices para melhorar performance de consultas frequentes
-- ============================================================
CREATE INDEX idx_postagem_usuario   ON postagem(usuario_id);
CREATE INDEX idx_postagem_grupo     ON postagem(grupo_id);
CREATE INDEX idx_ug_usuario         ON usuario_grupo(usuario_id);
CREATE INDEX idx_ug_grupo           ON usuario_grupo(grupo_id);
CREATE INDEX idx_arquivo_postagem   ON arquivo_postagem(postagem_id);
