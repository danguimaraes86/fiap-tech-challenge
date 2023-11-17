package br.com.fiap.techchallenge.infra.repositories;

import br.com.fiap.techchallenge.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CondutorRepository extends JpaRepository<Condutor, UUID> {

    Optional<Condutor> findCondutorByCpf(@Param("cpf") String cpf);

    Optional<List<Condutor>> findCondutorByCpfOrNomeIgnoreCaseOrEmail(
            @Param("cpf") String cpf, @Param("nome") String nome, @Param("email") String email
    );
}
