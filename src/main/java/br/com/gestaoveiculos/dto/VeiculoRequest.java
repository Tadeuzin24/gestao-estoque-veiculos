
package br.com.gestaoveiculos.dto;

import br.com.gestaoveiculos.entity.StatusVeiculo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VeiculoRequest(

        @NotNull(message = "O modelo é obrigatório")
        Long modeloId,

        @NotNull(message = "O ano de fabricação é obrigatório")
        @Min(value = 1886, message = "Ano de fabricação inválido")
        Integer anoFabricacao,

        @NotBlank(message = "A cor é obrigatória")
        @Size(max = 50)
        String cor,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01")
        @Digits(integer = 10, fraction = 2)
        BigDecimal preco,

        @NotNull(message = "A quilometragem é obrigatória")
        @Min(value = 0)
        Integer quilometragem,

        @NotNull(message = "O status é obrigatório")
        StatusVeiculo status

) {
}
