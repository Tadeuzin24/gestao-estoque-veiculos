package br.com.gestaoveiculos.dto;

public record ModeloResponse(
        Long id,
        String nome,
        Long marcaId,
        String marcaNome
) {
}