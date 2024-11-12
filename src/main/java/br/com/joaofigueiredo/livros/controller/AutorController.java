package br.com.joaofigueiredo.livros.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

import br.com.joaofigueiredo.livros.dto.AutorDTO;
import br.com.joaofigueiredo.livros.model.Autor;
import br.com.joaofigueiredo.livros.repository.AutorRepository;
import br.com.joaofigueiredo.livros.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/autores")
public class AutorController {

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private AutorService autorService;

	@GetMapping
	public List<Autor> listarAutores() {
		return autorRepository.findAll();
	}

	@Operation(summary = "Busca um autor pelo ID", description = "Retorna um autor específico com base no ID fornecido.")
	@GetMapping("/{id}")
	public ResponseEntity<AutorDTO> obterAutor(@Parameter(description = "ID do autor") @PathVariable UUID id) {
		Optional<AutorDTO> autor = autorService.obterAutor(id);
		return autor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Autor> criarAutor(@RequestBody Autor autor) {
		Autor novoAutor = autorService.criarAutor(autor);
		return ResponseEntity.status(HttpStatus.CREATED).body(novoAutor);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Autor> atualizarAutor(@PathVariable UUID id, @RequestBody Autor detalhesAutor) {
		Optional<Autor> autorAtualizado = autorService.atualizarAutor(id, detalhesAutor);
		return autorAtualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirAutor(@PathVariable UUID id) {
		boolean excluido = autorService.excluirAutor(id);
		if (excluido) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/livro/{idLivro}")
	public ResponseEntity<List<Autor>> buscarAutoresPorLivro(@PathVariable UUID idLivro) {
		List<Autor> autores = autorService.buscarAutoresPorLivro(idLivro);
		return ResponseEntity.ok(autores);
	}
}
