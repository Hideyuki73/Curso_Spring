package io.github.Hideyuki73.rest.dto;

import io.github.Hideyuki73.domain.validation.NotNonOption;
import lombok.Data;

@Data
public class AtualizacaoStatusPedidoDTO {

    @NotNonOption(message = "O status so pode ser alterado para CANCELADO ou REALIZADO")
    private String novoStatus;
}
