package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.repository;

import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.entity.Eletrodomestico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Long> {
}
