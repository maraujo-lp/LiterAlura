package com.literalura.dto;

public record LivroDTO(
        String titulo,
        String autor,
        Integer anoNascimento,
        Integer anoFalecimento,
        String idioma,
        Integer downloads
) {
}