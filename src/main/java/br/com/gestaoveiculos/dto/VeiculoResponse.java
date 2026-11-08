
package br.com.gestaoveiculos.dto;

import br.com.gestaoveiculos.entity.StatusVeiculo;

import java.math.BigDecimal;

public record VeiculoResponse(

        Long id,
        Long modeloId,
        String modeloNome,
        String marcaNome,
        Integer anoFabricacao,
        String cor,
        BigDecimal preco,
        Integer quilometragem,
        StatusVeiculo status

) {
}
