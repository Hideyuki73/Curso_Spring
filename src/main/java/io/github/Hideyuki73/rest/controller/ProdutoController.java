package io.github.Hideyuki73.rest.controller;

import io.github.Hideyuki73.domain.entity.Produto;
import io.github.Hideyuki73.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    Produtos produtos;
    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping("/{id}")
    public Produto findById(@PathVariable Integer id) {
        return produtos.findById(id).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    @GetMapping
    public ResponseEntity findAll(Produto produto){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(produto, matcher);
        List<Produto> produtoList = produtos.findAll(example);
        return ResponseEntity.ok(produtoList);
    }

    @PostMapping("/saveProduto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduto(@RequestBody @Valid Produto produto) {
        return produtos.save( produto );
    }

    @DeleteMapping("/deleteProduto/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable Integer id) {
        produtos.findById(id).map( produto -> {
            produtos.delete( produto );
            return produto;
        }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    @PutMapping("/updateProduto/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        produtos.findById(id).map( produtoAtualizado -> {
            produto.setId(id);
            produtos.save( produto );
            return produto;
        }).orElseThrow( () ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

}
