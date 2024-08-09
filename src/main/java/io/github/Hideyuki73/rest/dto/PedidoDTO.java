package io.github.Hideyuki73.rest.dto;

import io.github.Hideyuki73.domain.validation.NotEmptyList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter

public class PedidoDTO {
    @NotNull(message = "Campo total obrigatorio")
    private Integer cliente;
    @NotNull(message = "Campo total obrigatorio")
    private BigDecimal total;
    @NotEmptyList(message = "O pedido nao pode ser realizado sem itens")
    private List<ItemPedidoDTO> items;

}
