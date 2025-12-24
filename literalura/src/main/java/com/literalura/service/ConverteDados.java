package com.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literalura.dto.LivroDTO;

public class ConverteDados {

    public LivroDTO obterDados(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode raiz = mapper.readTree(json);

            JsonNode livro = raiz.get("results").get(0);

            String titulo = livro.get("title").asText();
            String idioma = livro.get("languages").get(0).asText();
            Integer downloads = livro.get("download_count").asInt();

            JsonNode autorNode = livro.get("authors").get(0);
            String autor = autorNode.get("name").asText();

            Integer anoNascimento = null;
            Integer anoFalecimento = null;

            if (autorNode.has("birth_year") && !autorNode.get("birth_year").isNull()) {
                anoNascimento = autorNode.get("birth_year").asInt();
            }

            if (autorNode.has("death_year") && !autorNode.get("death_year").isNull()) {
                anoFalecimento = autorNode.get("death_year").asInt();
            }

            return new LivroDTO(titulo, autor, anoNascimento, anoFalecimento, idioma, downloads);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter dados");
        }
    }
}