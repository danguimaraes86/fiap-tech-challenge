package br.com.fiap.techchallenge.carrinho.repository;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarrinhoFinalizadoRepository extends MongoRepository<CarrinhoFinalizado, String> {
}
