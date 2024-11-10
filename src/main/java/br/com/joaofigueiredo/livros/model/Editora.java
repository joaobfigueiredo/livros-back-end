package br.com.joaofigueiredo.livros.model;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "editoras")
@Data
public class Editora {

    @Id
    @GeneratedValue
    private UUID idEditora;

    private String nome;
    private String telefone;
    private String email;
    private String website;

    @OneToMany(mappedBy = "editora")
    @JsonIgnoreProperties("editora")
    private Set<Livro> livros;

}
