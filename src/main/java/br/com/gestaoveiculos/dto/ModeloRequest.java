package br.com.gestaoveiculos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModeloRequest(

        @NotBlank(message = "O nome do modelo é obrigatório")
        @Size(max = 100)
        String nome,

        @NotNull(message = "A marca é obrigatória")
        Long marcaId

) {
}