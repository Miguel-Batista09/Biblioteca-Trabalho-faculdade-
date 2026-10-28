CREATE DATABASE biblioteca_db;
USE biblioteca_db;

CREATE TABLE livros (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    categoria VARCHAR(100) NOT NULL, -- Economia, Direito, Teologia, etc.
    ano_publicacao INT,
    descricao TEXT
);