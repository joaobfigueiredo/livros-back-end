package br.com.joaofigueiredo.livros.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaofigueiredo.livros.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID>{

	List<Autor> findByLivros_IdLivro(UUID idLivro);
	

}
