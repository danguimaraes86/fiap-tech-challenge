package br.com.fiap.techchallenge.produtos.repositories;

import br.com.fiap.techchallenge.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
    Optional<Produto> findByNomeIgnoreCase(String nome);
}
