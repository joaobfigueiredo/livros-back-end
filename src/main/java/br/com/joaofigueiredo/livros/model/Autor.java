package br.com.joaofigueiredo.livros.model;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "autores")
@Getter
@Setter
public class Autor {

    @Id
    @GeneratedValue
    private UUID idAutor;

    private String nome;
    private String biografia;
    private String nacionalidade;

    @ManyToMany(mappedBy = "autores")
    @JsonIgnoreProperties("autores")
    private Set<Livro> livros;
    
}
