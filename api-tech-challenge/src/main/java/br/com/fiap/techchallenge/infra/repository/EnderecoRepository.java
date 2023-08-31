package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByNomeInstalacaoIgnoreCaseOrEstadoIgnoreCaseOrCidadeIgnoreCaseOrBairroIgnoreCase(
            @Param("nomeInstalacao") String nomeInstalacao,
            @Param("estado") String estado,
            @Param("cidade") String cidade,
            @Param("bairro") String bairro
    );
}
