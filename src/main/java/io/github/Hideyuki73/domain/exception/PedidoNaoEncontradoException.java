package io.github.Hideyuki73.domain.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }
}
