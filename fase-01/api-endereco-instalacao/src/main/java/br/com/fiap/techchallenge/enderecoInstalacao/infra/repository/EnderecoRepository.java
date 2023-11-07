package br.com.fiap.techchallenge.enderecoInstalacao.infra.repository;

import br.com.fiap.techchallenge.enderecoInstalacao.domain.entidade.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
