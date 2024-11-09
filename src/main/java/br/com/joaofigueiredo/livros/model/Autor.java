package br.com.joaofigueiredo.livros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;
import java.util.Set;

@Entity
@Table(name = "autores")
@Data
public class Autor {

    @Id
    @GeneratedValue
    private UUID idAutor;

    private String nome;
    private String biografia;
    private String nacionalidade;

    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros;


}
