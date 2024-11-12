package br.com.joaofigueiredo.livros.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livros")
@Getter
@Setter
public class Livro {

    @Id
    @GeneratedValue
    private UUID idLivro;

    private String titulo;
    private Integer anoPublicacao;
    private String genero;

    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
    

}
