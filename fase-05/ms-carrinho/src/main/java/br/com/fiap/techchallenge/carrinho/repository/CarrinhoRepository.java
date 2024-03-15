package br.com.fiap.techchallenge.carrinho.repository;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends MongoRepository<CarrinhoAberto, String> {

}
