package br.com.joaofigueiredo.livros.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaofigueiredo.livros.model.Autor;
import br.com.joaofigueiredo.livros.repository.AutorRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> buscarAutoresPorLivro(UUID idLivro) {
        return autorRepository.findByLivros_IdLivro(idLivro);
    }

	public List<Autor> findAllById(Set<UUID> idsAutores) {
		return autorRepository.findAllById(idsAutores);
	}
}
