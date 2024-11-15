CREATE SCHEMA `db_livros`;


CREATE TABLE Autores (
    id_autor BINARY(16) PRIMARY KEY DEFAULT (UUID()),
    nome VARCHAR(255) NOT NULL,
    biografia TEXT,
    nacionalidade VARCHAR(100)
);
CREATE TABLE Editoras (
    id_editora BINARY(16) PRIMARY KEY DEFAULT (UUID()),
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    website VARCHAR(100)
);
-- Tabela Livros
CREATE TABLE Livros (
    id_livro BINARY(16) PRIMARY KEY DEFAULT (UUID()),
    titulo VARCHAR(255) NOT NULL,
    ano_publicacao INTEGER,
    genero VARCHAR(100),
    id_editora BINARY(16),
    FOREIGN KEY (id_editora) REFERENCES Editoras(id_editora) ON DELETE SET NULL
);

-- Tabela Livro_Autores (para a relação muitos-para-muitos entre Livros e Autores)
CREATE TABLE Livro_Autores (
    id_livro BINARY(16),
    id_autor BINARY(16),
    PRIMARY KEY (id_livro, id_autor),
    FOREIGN KEY (id_livro) REFERENCES Livros(id_livro) ON DELETE CASCADE,
    FOREIGN KEY (id_autor) REFERENCES Autores(id_autor) ON DELETE CASCADE
);


