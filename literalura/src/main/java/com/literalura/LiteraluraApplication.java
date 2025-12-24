package com.literalura;

import com.literalura.service.LiterAluraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LiterAluraService service;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		int opcao = -1;

		while (opcao != 0) {
			System.out.println("""
                    
                    ===== LITERALURA =====
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    0 - Sair
                    """);

			opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
				case 1 -> {
					System.out.print("Digite o título do livro: ");
					String titulo = scanner.nextLine();
					service.buscarLivroNaApi(titulo);
				}
				case 2 -> service.listarLivros();
				case 3 -> service.listarAutores();
				case 4 -> {
					System.out.print("Digite o ano: ");
					Integer ano = scanner.nextInt();
					service.listarAutoresVivosNoAno(ano);
				}
				case 5 -> {
					System.out.print("Digite o idioma (pt, en, es, fr): ");
					String idioma = scanner.nextLine();
					service.listarLivrosPorIdioma(idioma);
				}
				case 0 -> System.out.println("Saindo...");
				default -> System.out.println("Opção inválida");
			}
		}
	}
}