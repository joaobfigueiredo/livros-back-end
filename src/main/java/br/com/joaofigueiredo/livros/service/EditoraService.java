package br.com.joaofigueiredo.livros.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.joaofigueiredo.livros.model.Editora;
import br.com.joaofigueiredo.livros.repository.EditoraRepository;

@Service
public class EditoraService {
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	public List<Editora> findAllById(Set<UUID> idsEditoras) {
		return editoraRepository.findAllById(idsEditoras);
	}

	public List<Editora> listarEditoras() {
		return editoraRepository.findAll();
	}

    public Optional<Editora> obterEditora(UUID id) {
        return editoraRepository.findById(id);
    }

    public Editora criarEditora(Editora editora) {
        return editoraRepository.save(editora);
    }

	public Optional<Editora> atualizarEditora(UUID id, Editora detalhesEditora) {
		Optional<Editora> editoraExistente = editoraRepository.findById(id);
		if (editoraExistente.isPresent()) {
			Editora editora = editoraExistente.get();
			editora.setNome(detalhesEditora.getNome());
			editora.setTelefone(detalhesEditora.getTelefone());
			editora.setEmail(detalhesEditora.getEmail());
			editora.setWebsite(detalhesEditora.getWebsite());
			return Optional.of(editoraRepository.save(editora));
		} else {
			return Optional.empty();
		}
	}
	
    public boolean excluirEditora(UUID id) {
        if (editoraRepository.existsById(id)) {
            editoraRepository.deleteById(id);
            return true;
        }
        return false;
    }	

}