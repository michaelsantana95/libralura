package com.example.Challenge.LiterAlura.main;

import com.example.Challenge.LiterAlura.model.*;
import com.example.Challenge.LiterAlura.repository.AutorRepository;
import com.example.Challenge.LiterAlura.repository.LivroRepository;
import com.example.Challenge.LiterAlura.service.ConectaAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private String enderecoBuscaLivro = "https://gutendex.com/books/?search=";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Scanner scanner = new Scanner(System.in);
    private boolean loop = true;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void run() throws JsonProcessingException {
        System.out.println();
        System.out.println("***************************************");
        System.out.println("*      Bem vindos ao LiterAlura       *");
        System.out.println("*  Por: Jhonathan Pfeiffer Urbainski  *");
        System.out.println("***************************************");
        while (loop) {
            System.out.print("""
                
                1 - Buscar Online
                2 - Listar todos os livros armazenados
                3 - Listar livros por título escolhido
                4 - Listar todos os autores armazenados
                5 - Listar autores vivos em um determinado ano
                6 - Listar livros por idioma (todos)
                7 - Listar livros por idioma escolhido
                8 - Listar top 10 livros mais baixados
                
                0 - Sair
                
                Opção:\s""");
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                System.out.println();
                switch (opcao) {
                    case 0:
                        System.out.println("Saindo...");
                        System.out.println();
                        loop = false;
                        break;
                    case 1:
                        buscaLivro();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 2:
                        listarTodosOsLivros();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 3:
                        listarLivrosPorTituloEscolhido();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 4:
                        listarTodosOsAutores();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 5:
                        listarAutoresVivosPorAno();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 6:
                        listarLivrosPorIdiomaTodos();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 7:
                        listarLivrosPorIdiomaEscolhido();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    case 8:
                        listarTop10Livros();
                        System.out.println();
                        System.out.println("--Fim--");
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println("Opção inválida");
                scanner.nextLine();
            }
        }
    }

    private void previaBuscaLivro(DadosLivro dadosLivro){
        String titulo = dadosLivro.titulo();
        if (titulo.length() > 25) {
            titulo = titulo.substring(0, 25) + "...";
        }
        String tituloFormatado = String.format("%-28s", titulo);
        String autorFormatado = dadosLivro.autores().stream()
                .findFirst()
                .map(autor -> {
                    String nome = autor.nome();
                    if (nome.length() > 25) {
                        nome = nome.substring(0, 25) + "...";
                    }
                    return String.format("%-28s", nome);
                })
                .orElse(String.format("%-28s", "Desconhecido"));
        String idiomaFormatado = Idioma.getNomeIdiomaByCodigoIdioma(dadosLivro.idioma().get(0));
        System.out.printf("%s | %s | %s%n", tituloFormatado, autorFormatado, idiomaFormatado);
    }

    private void buscaLivro() throws JsonProcessingException {
        ConectaAPI conectaAPI = new ConectaAPI();
        System.out.print("Nome para busca: ");
        String nomeLivro = scanner.nextLine().replace(" ", "+");
        System.out.println();
        System.out.println("Buscando...");
        System.out.println();
        String json = conectaAPI.obterDados(enderecoBuscaLivro + nomeLivro);
        Dados dados = objectMapper.readValue(json, Dados.class);
        if (dados.quantidadeDeResultadosEncontrados() == 0) {
            System.out.println("A busca não encontrou nada");
        } else {

            System.out.println("Prévia dos dados encontrados:");
            System.out.println();
            System.out.println("            LIVRO            |            AUTOR             | IDIOMA");
            System.out.println("--------------------------------------------------------------------");

            for (DadosLivro dadosLivro : dados.dadosLivro()) {
                Livro livro = livroRepository.findByTitulo(dadosLivro.titulo());

                ////////////////////////////////////////
                previaBuscaLivro(dadosLivro);
                ////////////////////////////////////////

                if (livro == null) {
                    livro = new Livro(dadosLivro);
                    for (DadosAutor dadosAutor : dadosLivro.autores()) {
                        Autor autor = autorRepository.findByNome(dadosAutor.nome());
                        if (autor == null) {
                            autor = new Autor(dadosAutor);
                            autorRepository.save(autor);
                        }
                        livro.getAutores().add(autor);
                    }
                    livroRepository.save(livro);
                }
            }
            System.out.println("--------------------------------------------------------------------");
            System.out.println();
            System.out.println("Dados salvos no banco de dados");
        }
    }

    private void listarTodosOsLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.println("Lista de todos os livros:");
            livros.stream()
                    .sorted(Comparator.comparing(Livro::getTitulo))
                    .forEach(livro -> {
                        System.out.println();
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("Livro: " + livro.getTitulo());
                        System.out.println("Idioma: " + livro.getIdioma().getNomeIdioma());
                        System.out.print("Autores: ");
                        String autores = livro.getAutores().stream()
                                .map(autor -> autor.converteNomePadraoBR(autor.getNome()))
                                .collect(Collectors.joining(", "));
                        System.out.println(autores);
                        System.out.println("--------------------------------------------------------------------");
                    });
        }
    }

    private void listarLivrosPorTituloEscolhido() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.print("Digite o título ou parte do título do livro: ");
            String tituloEscolhido = scanner.nextLine().trim();


            List<Livro> livrosFiltrados = livros.stream()
                    .filter(livro -> livro.getTitulo().toLowerCase().contains(tituloEscolhido.toLowerCase()))
                    .collect(Collectors.toList());

            if (livrosFiltrados.isEmpty()) {
                System.out.println();
                System.out.println("Nenhum livro encontrado");
            } else {
                System.out.println();
                System.out.println("Livros encontrados:");
                livrosFiltrados.forEach(livro -> {
                    System.out.println();
                    System.out.println("--------------------------------------------------------------------");
                    System.out.println("Título: " + livro.getTitulo());
                    System.out.println("Idioma: " + livro.getIdioma().getNomeIdioma());
                    System.out.print("Autores: ");
                    String autores = livro.getAutores().stream()
                            .map(autor -> autor.converteNomePadraoBR(autor.getNome()))
                            .collect(Collectors.joining(", "));
                    System.out.println(autores);
                    System.out.println("--------------------------------------------------------------------");
                });
            }
        }
    }

    private void listarTodosOsAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.println("Lista de todos os autores:");
            System.out.println();
            autores.stream()
                    .map(autor -> "Autor: " + autor.converteNomePadraoBR(autor.getNome()))
                    .sorted(String::compareTo)
                    .forEach(System.out::println);
        }
    }

    private void listarAutoresVivosPorAno() {
        if (autorRepository.findAll().isEmpty()){
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.println("Qual ano deseja buscar?");
            System.out.println();
            System.out.print("Ano: ");
            int ano = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            List<Autor> autores = autorRepository.findAutorVivoEmAno(ano);
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado");
            } else {
                System.out.println("Lista de autores vivos no ano escolhido:");
                System.out.println();
                autores.stream()
                        .map(autor -> {
                            autor.setNome(autor.converteNomePadraoBR(autor.getNome()));
                            return autor;
                        })
                        .sorted(Comparator.comparing(Autor::getAnoNascimento))
                        .forEach(autor -> {
                            System.out.println("Nascimento: " + autor.getAnoNascimento() + " | Morte: " + autor.getAnoMorte() + " | Autor: " + autor.getNome());
                        });
            }
        }
    }

    private void listarLivrosPorIdiomaTodos() {
        List<Idioma> idiomas = livroRepository.listarIdiomas();
        if (idiomas.isEmpty()) {
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.println("Lista de livros por idioma:");
            System.out.println();
            idiomas.forEach(idioma -> {
                System.out.println("Idioma: " + idioma.getNomeIdioma());
                List<Livro> livros = livroRepository.findByIdioma(idioma);
                livros.forEach(livro -> System.out.println("  Livro: " + livro.getTitulo()));
                System.out.println();
            });
        }
    }

    private void listarLivrosPorIdiomaEscolhido() {
        List<Idioma> idiomas = livroRepository.listarIdiomas();
        if (idiomas.isEmpty()) {
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.println("Idiomas presentes no banco de dados:");
            System.out.println();
            System.out.println("Código | Idioma");
            idiomas.forEach(idioma -> System.out.println("  " + idioma.getCodigoIdioma().toUpperCase() + "   - " + idioma.getNomeIdioma()));
            System.out.println();
            System.out.println("Qual idioma deseja buscar?");
            System.out.println();
            System.out.print("Código idioma: ");
            while (true) {
                try {
                    String idiomaEscolhido = scanner.nextLine();
                    List<Livro> livros = livroRepository.findByIdioma(Idioma.valueOf(idiomaEscolhido.toUpperCase()));
                    System.out.println();
                    System.out.println("Lista de livros em " + Idioma.getNomeIdiomaByCodigoIdioma(idiomaEscolhido) + ":");
                    System.out.println();
                    livros.forEach(livro -> System.out.println(" Livro: " + livro.getTitulo()));
                    System.out.println();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println();
                    System.out.println("Código inválido");
                    System.out.println();
                    System.out.print("Código idioma: ");
                }
            }
        }
    }

    private void listarTop10Livros() {
        List<Livro> top10Livros = livroRepository.findTop10ByOrderByDownloadsDesc();
        if (top10Livros.isEmpty()) {
            System.out.println("O banco de dados está vazio");
        } else {
            System.out.println("Top 10 livros mais baixados:");
            System.out.println();
            top10Livros.forEach(livro -> {
                System.out.println("Downloads: " + livro.getDownloads() + " | Livro: " + livro.getTitulo());
            });
        }
    }
}
