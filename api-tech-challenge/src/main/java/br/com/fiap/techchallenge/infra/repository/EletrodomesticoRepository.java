package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Long> {
    List<Eletrodomestico> findByNomeIgnoreCaseOrModeloIgnoreCaseOrFabricacaoAfter(
            @Param("nome") String nome,
            @Param("modelo") String modelo,
            @Param("fabricacao") LocalDate fabricacao
    );
}
