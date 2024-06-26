package com.example.Challenge.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 1024)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private int downloads;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "livro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores = new ArrayList<>();

    public Livro(){}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = Idioma.setCodigoIdioma(dadosLivro.idioma().get(0).toUpperCase());
        this.downloads = dadosLivro.downloads();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

//    public String getIdioma() {
//        return idioma;
//    }
//
//    public void setIdioma(String idioma) {
//        this.idioma = idioma;
//    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return titulo;
    }
}