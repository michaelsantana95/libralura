package com.example.Challenge.LiterAlura.repository;

import com.example.Challenge.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String nome);

    @Query("SELECT autor FROM Autor autor WHERE autor.anoMorte >= :ano AND autor.anoNascimento <= :ano")
    List<Autor> findAutorVivoEmAno(int ano);
}
