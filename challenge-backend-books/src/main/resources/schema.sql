
ALTER TABLE IF EXISTS AUTOR
DROP CONSTRAINT IF EXISTS IDX_AUTOR_NOME;

ALTER TABLE IF EXISTS ASSUNTO
DROP CONSTRAINT IF EXISTS IDX_ASSUNTO_DESCRICAO;

ALTER TABLE IF EXISTS LIVRO
DROP CONSTRAINT IF EXISTS IDX_LIVRO_TITULO;

ALTER TABLE IF EXISTS LIVRO_ASSUNTO
DROP CONSTRAINT IF EXISTS FK1_LIVRO_ASSUNTO;

ALTER TABLE IF EXISTS LIVRO_ASSUNTO
DROP CONSTRAINT IF EXISTS FK2_LIVRO_ASSUNTO;

ALTER TABLE IF EXISTS LIVRO_AUTOR
DROP CONSTRAINT IF EXISTS FK1_LIVRO_AUTOR;

ALTER TABLE IF EXISTS LIVRO_AUTOR
DROP CONSTRAINT IF EXISTS FK2_LIVRO_AUTOR;

DROP TABLE IF EXISTS AUTOR;
DROP TABLE IF EXISTS ASSUNTO;
DROP TABLE IF EXISTS LIVRO;
DROP TABLE IF EXISTS LIVRO_ASSUNTO;
DROP TABLE IF EXISTS LIVRO_AUTOR;

CREATE TABLE IF NOT EXISTS AUTOR (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    NOME VARCHAR(40) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS IDX_AUTOR_NOME ON AUTOR (NOME);

CREATE TABLE IF NOT EXISTS ASSUNTO (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    DESCRICAO VARCHAR(20) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS IDX_ASSUNTO_DESCRICAO ON ASSUNTO (DESCRICAO);

CREATE TABLE IF NOT EXISTS LIVRO (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    TITULO VARCHAR(40) NOT NULL,
    EDITORA VARCHAR(40) NOT NULL,
    EDICAO INTEGER NOT NULL,
    ANO_PUBLICACAO VARCHAR(4) NOT NULL,
    PRECO NUMERIC(10,2) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS IDX_LIVRO_TITULO ON LIVRO (TITULO);

CREATE TABLE IF NOT EXISTS LIVRO_ASSUNTO (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    ID_LIVRO INTEGER NOT NULL,
    ID_ASSUNTO INTEGER NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK1_LIVRO_ASSUNTO
    FOREIGN KEY (ID_LIVRO) REFERENCES LIVRO (ID),
    CONSTRAINT FK2_LIVRO_ASSUNTO
    FOREIGN KEY (ID_ASSUNTO) REFERENCES ASSUNTO (ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS IDX_LIVRO_ASSUNTO ON LIVRO_ASSUNTO (ID_LIVRO, ID_ASSUNTO);

CREATE TABLE IF NOT EXISTS LIVRO_AUTOR (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    ID_LIVRO INTEGER NOT NULL,
    ID_AUTOR INTEGER NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT FK1_LIVRO_AUTOR
    FOREIGN KEY (ID_LIVRO) REFERENCES LIVRO (ID),
    CONSTRAINT FK2_LIVRO_AUTOR
    FOREIGN KEY (ID_AUTOR) REFERENCES AUTOR (ID)
);

CREATE UNIQUE INDEX IF NOT EXISTS IDX_LIVRO_AUTOR ON LIVRO_AUTOR (ID_LIVRO, ID_AUTOR);
