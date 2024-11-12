package br.com.joaofigueiredo.livros.dto;

import java.util.UUID;

public record EditoraDTO(UUID idEditora, String telefone, String email, String website) {

}
