package com.example.Challenge.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, length = 1024)
    private String nome;
    private int anoNascimento;
    private int anoMorte;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}

    public Autor(DadosAutor dadosAutor){
        this.nome = dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoMorte = dadosAutor.anoMorte();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(int anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public int getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(int anoMorte) {
        this.anoMorte = anoMorte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public String converteNomePadraoBR(String nome) {
        String[] partes = nome.split(", ");
        if (partes.length == 2) {
            return this.nome = partes[1] + " " + partes[0];
        } else if (partes.length == 3) {
            return this.nome = partes[1] + " " + partes[2] + " " + partes[0];
        } else {
            return this.nome = nome;
        }
    }

    @Override
    public String toString() {
        return "Autor: " + nome;
    }
}