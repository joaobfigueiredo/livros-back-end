package br.com.joaofigueiredo.livros.service;

import java.util.List;
import java.util.Optional;
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

	public List<Autor> listarAutores() {
		return autorRepository.findAll();
	}

    public Optional<Autor> obterAutor(UUID id) {
        return autorRepository.findById(id);
    }

    public Autor criarAutor(Autor autor) {
        return autorRepository.save(autor);
    }

	public Optional<Autor> atualizarAutor(UUID id, Autor detalhesAutor) {
		Optional<Autor> autorExistente = autorRepository.findById(id);
		if (autorExistente.isPresent()) {
			Autor autor = autorExistente.get();
			autor.setNome(detalhesAutor.getNome());
			autor.setBiografia(detalhesAutor.getBiografia());
			autor.setNacionalidade(detalhesAutor.getNacionalidade());
			return Optional.of(autorRepository.save(autor));
		} else {
			return Optional.empty();
		}
	}
	
    public boolean excluirAutor(UUID id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return true;
        }
        return false;
    }	
}
