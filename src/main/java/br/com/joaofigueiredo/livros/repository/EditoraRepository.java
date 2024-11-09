package br.com.joaofigueiredo.livros.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaofigueiredo.livros.model.Editora;

public interface EditoraRepository extends JpaRepository<Editora, UUID> {
}