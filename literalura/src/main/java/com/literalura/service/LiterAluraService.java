package com.literalura.service;

import com.literalura.dto.LivroDTO;
import com.literalura.model.Autor;
import com.literalura.model.Livro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiterAluraService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    public LiterAluraService(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void buscarLivroNaApi(String nomeLivro) {
        String endereco = "https://gutendex.com/books/?search=" + nomeLivro.replace(" ", "%20");
        String json = consumoApi.obterDados(endereco);
        LivroDTO livroDTO = converteDados.obterDados(json);

        if (livroDTO != null) {
            Autor autor = new Autor(livroDTO.autor(), livroDTO.anoNascimento(), livroDTO.anoFalecimento());
            autorRepository.save(autor);

            Livro livro = new Livro(livroDTO.titulo(), livroDTO.idioma(), livroDTO.downloads(), autor);
            livroRepository.save(livro);

            System.out.println("\nLivro salvo com sucesso!");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + autor.getNome());
        }
    }

    public void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        livros.forEach(l -> System.out.println(l.getTitulo() + " - " + l.getAutor().getNome()));
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(a -> System.out.println(a.getNome()));
    }

    public void listarAutoresVivosNoAno(Integer ano) {
        List<Autor> autores = autorRepository.findAutoresVivosNoAno(ano);
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo neste ano.");
        } else {
            autores.forEach(a -> System.out.println(a.getNome()));
        }
    }

    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado neste idioma.");
        } else {
            livros.forEach(l -> System.out.println(l.getTitulo() + " - " + l.getAutor().getNome()));
        }
    }
}