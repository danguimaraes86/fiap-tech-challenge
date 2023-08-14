package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
