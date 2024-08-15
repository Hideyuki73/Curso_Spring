package io.github.Hideyuki73.rest.controller;
import io.github.Hideyuki73.domain.entity.Cliente;
import io.github.Hideyuki73.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/api/clientes")
public class ClienteController2 {
    private Clientes clientes;

    public ClienteController2( Clientes clientes ) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}") //metodo get, pegar informacao
    public Cliente getClienteById(@PathVariable Integer id ){
        return clientes.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

    }

    @PostMapping("/saveCliente") //Metodo post, inserir informacao
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody @Valid Cliente cliente ){//@Valid para validar se tem os campos necessario
        return clientes.save( cliente );
    }

    @DeleteMapping("/deleteCliente/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClienteById( @PathVariable Integer id ){
        clientes.findById(id).map( cliente -> {
            clientes.delete(cliente);
            return cliente;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
    }

    @PutMapping("/updateCliente/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCliente(@PathVariable Integer id, @RequestBody @Valid Cliente cliente ){
         clientes.findById(id).map( clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clientes.save(cliente);
            return cliente;
        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
    }

    @GetMapping
    public List<Cliente> find ( Cliente cliente ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(cliente, matcher);
        return clientes.findAll(example);
    }
}
