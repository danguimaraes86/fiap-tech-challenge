package br.com.fiap.techchallenge.produtos.repositories;

import br.com.fiap.techchallenge.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
}
