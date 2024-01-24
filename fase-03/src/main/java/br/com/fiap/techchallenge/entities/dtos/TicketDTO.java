package br.com.fiap.techchallenge.entities.dtos;

import br.com.fiap.techchallenge.entities.Ticket;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(
        UUID uuid,
        LocalDateTime horarioEntrada,
        LocalDateTime horarioSaida,
        String tipoCobranca,
        String permanencia,
        Double valorTotal,
        @NotBlank
        String condutorCpf,
        @NotBlank
        String placaVeiculo
) {
    public Ticket toEntity(String condutor, String veiculo, LocalDateTime horarioEntrada) {
        return new Ticket(condutor, veiculo, horarioEntrada);
    }

}
