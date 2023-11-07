package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {

    List<Consumidor> findConsumidorByNomeIgnoreCaseOrSexoIgnoreCaseOrParentescoIgnoreCaseOrDataNascimentoBefore(
            @Param("nome") String nome,
            @Param("sexo") String sexo,
            @Param("parentesco") String parentesco,
            @Param("dataNascimento")LocalDate dataNascimento
            );

}
