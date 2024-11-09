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

import br.com.joaofigueiredo.livros.model.Autor;
import br.com.joaofigueiredo.livros.repository.AutorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    @Operation(summary = "Busca um autor pelo ID", description = "Retorna um autor específico com base no ID fornecido.")
    @GetMapping("/{id}")
    public ResponseEntity<Autor> obterAutor(@Parameter(description = "ID do autor") @PathVariable UUID id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Autor> criarAutor(@RequestBody Autor autor) {
        Autor novoAutor = autorRepository.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizarAutor(@PathVariable UUID id, @RequestBody Autor detalhesAutor) {
        Optional<Autor> autorExistente = autorRepository.findById(id);
        if (autorExistente.isPresent()) {
            Autor autor = autorExistente.get();
            autor.setNome(detalhesAutor.getNome());
            autor.setBiografia(detalhesAutor.getBiografia());
            autor.setNacionalidade(detalhesAutor.getNacionalidade());
            Autor autorAtualizado = autorRepository.save(autor);
            return ResponseEntity.ok(autorAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir um autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAutor(@PathVariable UUID id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

