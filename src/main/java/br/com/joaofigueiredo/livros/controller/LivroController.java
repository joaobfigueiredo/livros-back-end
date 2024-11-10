package br.com.joaofigueiredo.livros.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaofigueiredo.livros.model.Autor;
import br.com.joaofigueiredo.livros.model.Editora;
import br.com.joaofigueiredo.livros.model.Livro;
import br.com.joaofigueiredo.livros.repository.EditoraRepository;
import br.com.joaofigueiredo.livros.repository.LivroRepository;
import br.com.joaofigueiredo.livros.service.AutorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private EditoraRepository editoraRepository;

	@Autowired
	private AutorService autorService;

	@GetMapping
	public List<Livro> listarLivros() {
		List<Livro> retorno = livroRepository.findAll().stream().peek(livro -> {
			Set<Autor> autores = new HashSet<>(autorService.buscarAutoresPorLivro(livro.getIdLivro()));
			livro.setAutores(autores); 
		}).collect(Collectors.toList());
		
		
		return retorno;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Livro> obterLivro(@PathVariable UUID id) {
		Optional<Livro> livro = livroRepository.findById(id);
	    if (livro.isPresent()) {
	        Set<Autor> autores = new HashSet<>(autorService.buscarAutoresPorLivro(id));
	        livro.get().setAutores(autores); 
	        return ResponseEntity.ok(livro.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping
	public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
		Livro novoLivro = livroRepository.save(livro);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Livro> atualizarLivro(@PathVariable UUID id, @RequestBody Livro detalhesLivro) {
		Optional<Livro> livroExistente = livroRepository.findById(id);
		if (livroExistente.isPresent()) {
			Livro livro = livroExistente.get();
			livro.setTitulo(detalhesLivro.getTitulo());
			livro.setAnoPublicacao(detalhesLivro.getAnoPublicacao());
			livro.setGenero(detalhesLivro.getGenero());
			livro.setEditora(detalhesLivro.getEditora());
			livro.setAutores(detalhesLivro.getAutores());
			Livro livroAtualizado = livroRepository.save(livro);
			return ResponseEntity.ok(livroAtualizado);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirLivro(@PathVariable UUID id) {
		if (livroRepository.existsById(id)) {
			livroRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}/editora/{idEditora}")
	public ResponseEntity<Livro> associarEditora(@PathVariable UUID id, @PathVariable UUID idEditora) {
		Optional<Livro> livroOptional = livroRepository.findById(id);
		Optional<Editora> editoraOptional = editoraRepository.findById(idEditora);

		if (livroOptional.isPresent() && editoraOptional.isPresent()) {
			Livro livro = livroOptional.get();
			livro.setEditora(editoraOptional.get());
			livroRepository.save(livro);
			return ResponseEntity.ok(livro);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}/autores")
	public ResponseEntity<Livro> associarAutores(@PathVariable UUID id, @RequestBody Set<UUID> idsAutores) {
		Optional<Livro> livroOptional = livroRepository.findById(id);

		if (livroOptional.isPresent()) {
			Livro livro = livroOptional.get();
			Set<Autor> autores = autorService.findAllById(idsAutores).stream().collect(Collectors.toSet());
			livro.setAutores(autores);
			livroRepository.save(livro);
			return ResponseEntity.ok(livro);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
