package com.example.Challenge.LiterAlura;

import com.example.Challenge.LiterAlura.main.Principal;
import com.example.Challenge.LiterAlura.repository.AutorRepository;
import com.example.Challenge.LiterAlura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void run(String... args) throws Exception {
		Principal main = new Principal(livroRepository, autorRepository);
		main.run();
	}
}
