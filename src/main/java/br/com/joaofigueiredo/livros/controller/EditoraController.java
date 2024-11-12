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

import br.com.joaofigueiredo.livros.model.Editora;
import br.com.joaofigueiredo.livros.service.EditoraService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @GetMapping
    public List<Editora> listarEditoras() {
        return editoraService.listarEditoras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editora> obterEditora(@PathVariable UUID id) {
        Optional<Editora> editora = editoraService.obterEditora(id);
        return editora.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Editora> criarEditora(@RequestBody Editora editora) {
        Editora novaEditora = editoraService.criarEditora(editora);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEditora);
    }

	@PutMapping("/{id}")
	public ResponseEntity<Editora> atualizarEditora(@PathVariable UUID id, @RequestBody Editora detalhesEditora) {
		Optional<Editora> editoraAtualizada = editoraService.atualizarEditora(id, detalhesEditora);
		return editoraAtualizada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEditora(@PathVariable UUID id) {
    	boolean excluido = editoraService.excluirEditora(id);
        if (excluido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
