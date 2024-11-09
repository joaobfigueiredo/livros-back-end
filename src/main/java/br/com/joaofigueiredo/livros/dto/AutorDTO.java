package br.com.joaofigueiredo.livros.dto;

import java.util.UUID;

public record AutorDTO(UUID idAutor, String nome, String biografia, String nacionalidade) {

}
