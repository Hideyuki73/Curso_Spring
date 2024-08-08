package io.github.Hideyuki73.rest.controller;

import io.github.Hideyuki73.domain.exception.RegraNegocioException;
import io.github.Hideyuki73.domain.service.impl.PedidoNaoEncontradoException;
import io.github.Hideyuki73.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePersistenceException(PedidoNaoEncontradoException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
