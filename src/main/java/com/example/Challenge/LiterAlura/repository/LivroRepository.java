package com.example.Challenge.LiterAlura.repository;

import com.example.Challenge.LiterAlura.model.Idioma;
import com.example.Challenge.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Livro findByTitulo(String titulo);

    @Query("SELECT DISTINCT idioma FROM Livro")
    List<Idioma> listarIdiomas();

    List<Livro> findByIdioma(Idioma idioma);

    List<Livro> findTop10ByOrderByDownloadsDesc();
}
