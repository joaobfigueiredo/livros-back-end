package br.com.joaofigueiredo.livros.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import br.com.joaofigueiredo.livros.model.Livro;
import br.com.joaofigueiredo.livros.repository.LivroRepository;
import br.com.joaofigueiredo.livros.service.LivroService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;
    
	@Autowired
	private LivroRepository livroRepository;

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> obterLivro(@PathVariable UUID id) {
        Optional<Livro> livro = livroService.obterLivro(id);
        return livro.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        Livro novoLivro = livroService.criarLivro(livro);
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

    //@PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro_(@PathVariable UUID id, @RequestBody Livro detalhesLivro) {
        Optional<Livro> livroAtualizado = livroService.atualizarLivro(id, detalhesLivro);
        return livroAtualizado.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable UUID id) {
        boolean excluido = livroService.excluirLivro(id);
        return excluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/editora/{idEditora}")
    public ResponseEntity<Livro> associarEditora(@PathVariable UUID id, @PathVariable UUID idEditora) {
        Optional<Livro> livroAtualizado = livroService.associarEditora(id, idEditora);
        return livroAtualizado.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/autores")
    public ResponseEntity<Livro> associarAutores(@PathVariable UUID id, @RequestBody Set<UUID> idsAutores) {
        Optional<Livro> livroAtualizado = livroService.associarAutores(id, idsAutores);
        return livroAtualizado.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
