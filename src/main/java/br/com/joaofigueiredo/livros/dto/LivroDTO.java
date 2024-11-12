package br.com.joaofigueiredo.livros.dto;

import java.util.UUID;

public record LivroDTO(UUID id, String titulo, int anoPublicacao, EditoraDTO editora, AutorDTO autor) {

}
