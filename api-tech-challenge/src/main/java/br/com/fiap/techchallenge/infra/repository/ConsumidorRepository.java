package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {

}
