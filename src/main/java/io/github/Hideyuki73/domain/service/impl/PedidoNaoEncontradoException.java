package io.github.Hideyuki73.domain.service.impl;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }
}
