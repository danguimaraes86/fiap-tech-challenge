package br.com.fiap.techchallenge.carrinho.repository;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PedidoRepository extends MongoRepository<Long, CarrinhoFinalizado> {
    List<CarrinhoFinalizado> findByUsuarioId(String usuarioId);
}
