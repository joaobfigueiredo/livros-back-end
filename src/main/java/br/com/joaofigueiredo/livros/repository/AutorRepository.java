package br.com.joaofigueiredo.livros.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaofigueiredo.livros.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID>{

}
