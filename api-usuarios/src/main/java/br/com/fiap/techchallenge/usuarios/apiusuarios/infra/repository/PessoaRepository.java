package br.com.fiap.techchallenge.usuarios.apiusuarios.infra.repository;

import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
