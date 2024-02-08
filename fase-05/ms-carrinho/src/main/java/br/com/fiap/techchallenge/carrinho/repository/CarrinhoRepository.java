package br.com.fiap.techchallenge.carrinho.repository;

import br.com.fiap.techchallenge.carrinho.entities.Carrinho;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoRepository extends MongoRepository<Carrinho, Long> {
    private List<Carrinho> findByUsuarioId(Long usuarioId) {
        return null;
    }

}
