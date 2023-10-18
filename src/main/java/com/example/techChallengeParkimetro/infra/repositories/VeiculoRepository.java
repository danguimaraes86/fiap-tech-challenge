package com.example.techChallengeParkimetro.infra.repositories;

import com.example.techChallengeParkimetro.entities.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    Optional<Veiculo> findVeiculoByPlaca(@Param("placa") String placa);
}
