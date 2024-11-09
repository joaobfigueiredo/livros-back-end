package br.com.joaofigueiredo.livros.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaofigueiredo.livros.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

}
