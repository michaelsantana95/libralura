package com.example.Challenge.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(
        @JsonAlias("count")
        int quantidadeDeResultadosEncontrados,

        @JsonAlias("results")
        List<DadosLivro> dadosLivro){}