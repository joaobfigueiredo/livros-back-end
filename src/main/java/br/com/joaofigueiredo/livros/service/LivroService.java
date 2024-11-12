package br.com.joaofigueiredo.livros.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaofigueiredo.livros.model.Autor;
import br.com.joaofigueiredo.livros.model.Editora;
import br.com.joaofigueiredo.livros.model.Livro;
import br.com.joaofigueiredo.livros.repository.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private EditoraService editoraService;
	
	@Autowired
	private AutorService autorService;
	
	public List<Livro> findAllById(Set<UUID> idsLivros) {
		return livroRepository.findAllById(idsLivros);
	}

	public List<Livro> listarLivros() {
		List<Livro> retorno = livroRepository.findAll().stream().peek(livro -> {
			Set<Autor> autores = new HashSet<>(autorService.buscarAutoresPorLivro(livro.getIdLivro()));
			livro.setAutores(autores); 
		}).collect(Collectors.toList());
		
		
		return retorno;
	}
    public Optional<Livro> obterLivro(UUID id) {
        return livroRepository.findById(id);
    }

    public Livro criarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

	public Optional<Livro> atualizarLivro(UUID id, Livro detalhesLivro) {
		Optional<Livro> livroExistente = livroRepository.findById(id);
		if (livroExistente.isPresent()) {
			Livro livro = livroExistente.get();
			livro.setTitulo(detalhesLivro.getTitulo());
			livro.setAnoPublicacao(detalhesLivro.getAnoPublicacao());
			livro.setGenero(detalhesLivro.getGenero());
			livro.setEditora(detalhesLivro.getEditora());
			livro.setAutores(detalhesLivro.getAutores());
			return Optional.of(livroRepository.save(livro));
		} else {
			return Optional.empty();
		}
	}
	
    public boolean excluirLivro(UUID id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }	
    
    public Optional<Livro> associarEditora(UUID id, UUID idEditora) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        Optional<Editora> editoraOptional = editoraService.obterEditora(idEditora);

        if (livroOptional.isPresent() && editoraOptional.isPresent()) {
            Livro livro = livroOptional.get();
            livro.setEditora(editoraOptional.get());
            return Optional.of(livroRepository.save(livro));
        }
        return Optional.empty();
    }

    public Optional<Livro> associarAutores(UUID id, Set<UUID> idsAutores) {
        return livroRepository.findById(id).map(livro -> {
            Set<Autor> autores = autorService.findAllById(idsAutores).stream().collect(Collectors.toSet());
            livro.setAutores(autores);
            return livroRepository.save(livro);
        });
    }    

}
