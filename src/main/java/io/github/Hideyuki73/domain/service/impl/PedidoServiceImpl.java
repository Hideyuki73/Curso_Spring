package io.github.Hideyuki73.domain.service.impl;

import io.github.Hideyuki73.domain.entity.Cliente;
import io.github.Hideyuki73.domain.entity.ItemPedido;
import io.github.Hideyuki73.domain.entity.Pedido;
import io.github.Hideyuki73.domain.entity.Produto;
import io.github.Hideyuki73.domain.entity.enums.StatusPedido;
import io.github.Hideyuki73.domain.exception.PedidoNaoEncontradoException;
import io.github.Hideyuki73.domain.exception.RegraNegocioException;
import io.github.Hideyuki73.domain.repository.Clientes;
import io.github.Hideyuki73.domain.repository.ItemsPedido;
import io.github.Hideyuki73.domain.repository.Pedidos;
import io.github.Hideyuki73.domain.repository.Produtos;
import io.github.Hideyuki73.domain.service.PedidoService;
import io.github.Hideyuki73.rest.dto.ItemPedidoDTO;
import io.github.Hideyuki73.rest.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientes;
    private final Produtos produtos;
    private final ItemsPedido itemsPedido;

    @Override
    @Transactional //Faz rollback caso ocorra algum erro
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientes.findById(idCliente)
                .orElseThrow( () -> new RegraNegocioException("Codigo de cliente invalido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidoList = converterItems(pedido, pedidoDTO.getItems());
        repository.save(pedido); //Salva o pedido para gerar o ID necessario
        itemsPedido.saveAll(itemPedidoList);
        pedido.setItens(itemPedidoList);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id).map( pedido -> {
            pedido.setStatus(statusPedido);
            return repository.save(pedido);
        }).orElseThrow( () -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido ,List<ItemPedidoDTO> items) { //converte o pedido e os items em uma lista de itemspedido
        if(items.isEmpty()){
            throw new RegraNegocioException("Nao e possivel realizare um pedido sem items.");
        }

        return items.stream() //converte a lista em stream, para pode filtrar mais facilmente
                .map( dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtos.findById(idProduto)
                    .orElseThrow(() -> new RegraNegocioException("Codigo de produto invalido: " + idProduto));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
