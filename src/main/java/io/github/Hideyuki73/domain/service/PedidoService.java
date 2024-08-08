package io.github.Hideyuki73.domain.service;

import io.github.Hideyuki73.domain.entity.Pedido;
import io.github.Hideyuki73.domain.entity.enums.StatusPedido;
import io.github.Hideyuki73.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
