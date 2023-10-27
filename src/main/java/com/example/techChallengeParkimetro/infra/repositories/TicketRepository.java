package com.example.techChallengeParkimetro.infra.repositories;

import com.example.techChallengeParkimetro.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findTicketByCondutorAndVeiculoIgnoreCaseAndHorarioSaidaIsNull(
            @Param("condutor") String condutor,
            @Param("veiculo") String veiculo
    );

    List<Ticket> findTicketsByHorarioSaidaIsNull();
}
