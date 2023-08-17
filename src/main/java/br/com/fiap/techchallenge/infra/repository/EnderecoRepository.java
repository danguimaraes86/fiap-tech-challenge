package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
