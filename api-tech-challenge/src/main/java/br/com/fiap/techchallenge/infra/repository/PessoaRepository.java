package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Consumidor, Long> {

}
