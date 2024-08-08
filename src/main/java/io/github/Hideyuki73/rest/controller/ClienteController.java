package io.github.Hideyuki73.rest.controller;

import io.github.Hideyuki73.domain.entity.Cliente;
import io.github.Hideyuki73.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {
//
//    private Clientes clientes;
//
//    public ClienteController( Clientes clientes ) {
//        this.clientes = clientes;
//    }
//
//    @GetMapping("/api/clientes/{id}") //metodo get, pegar informacao
//    @ResponseBody
//    public ResponseEntity getClienteById( @PathVariable Integer id ){
//        Optional<Cliente> cliente = clientes.findById(id);
//
//        if(cliente.isPresent()){
//            return ResponseEntity.ok( cliente.get() );
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/api/SaveCliente") //Metodo post, inserir informacao
//    @ResponseBody// response = resposta / request = pedindo
//    public ResponseEntity saveCliente(@RequestBody Cliente cliente ){
//        Cliente savedCliente = clientes.save( cliente );
//        return ResponseEntity.ok( savedCliente );
//    }
//
//    @DeleteMapping("/api/DeleteCliente/{id}")
//    public ResponseEntity deleteClienteById( @PathVariable Integer id ){
//        Optional<Cliente> cliente = clientes.findById(id);
//
//        if(cliente.isPresent()){
//            clientes.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @PutMapping("api/UpdateCliente/{id}")
//    @ResponseBody
//    public ResponseEntity updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente ){
//        return clientes.findById(id).map( clienteExistente -> {
//            cliente.setId(clienteExistente.getId());
//            clientes.save(cliente);
//            return ResponseEntity.noContent().build();
//        }).orElseGet( () -> ResponseEntity.notFound().build() );
//    }
//
//    @GetMapping("/api/clientes")
//    public ResponseEntity find ( Cliente cliente ){
//        ExampleMatcher matcher = ExampleMatcher
//                .matching()
//                .withIgnoreCase()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//        Example example = Example.of(cliente, matcher);
//        List<Cliente> list = clientes.findAll(example);
//        return ResponseEntity.ok( list );
//    }
}
