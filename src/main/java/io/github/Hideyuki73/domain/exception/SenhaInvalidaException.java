package io.github.Hideyuki73.domain.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha invalida");
    }
}
