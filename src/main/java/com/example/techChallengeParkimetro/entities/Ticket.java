package com.example.techChallengeParkimetro.entities;

import com.example.techChallengeParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengeParkimetro.infra.enums.TipoCobranca;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Ticket {

    private static final String VAZIO = "em aberto";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private LocalDateTime horarioEntrada;
    private LocalDateTime horarioSaida;
    @NotNull
    private TipoCobranca tipoCobranca;
    private Duration permanencia;
    private Double valorTotal;
    @NotNull
    private String veiculo;
    @NotNull
    private String condutor;

    public Ticket(
            String condutor,
            String veiculo,
            LocalDateTime horarioEntrada
    ) {
        this.condutor = condutor;
        this.veiculo = veiculo;
        this.horarioEntrada = horarioEntrada;
    }

    public void registarHorarioSaida() {
        this.horarioSaida = LocalDateTime.now();
        if (this.tipoCobranca.equals(TipoCobranca.FLEXIVEL)) {
            this.permanencia = Duration.ofMinutes(this.horarioEntrada.until(this.horarioSaida, ChronoUnit.MINUTES));
        }
        this.calcularValorTotal();
    }

    private void calcularValorTotal() {
        this.valorTotal = this.tipoCobranca.calcular(this.permanencia);
    }

    public TicketDTO toDTO() {
        String permanencia = this.horarioSaida == null && this.tipoCobranca.equals(TipoCobranca.FLEXIVEL) ?
                Ticket.VAZIO : String.valueOf(this.permanencia.toMinutes());

        return new TicketDTO(this.id, this.horarioEntrada, this.horarioSaida, this.tipoCobranca.name(),
                permanencia, this.valorTotal, this.condutor, this.veiculo
        );
    }

    public Boolean isEmAberto() {
        return horarioSaida == null;
    }

    public void handleTipoCobranca(TicketDTO ticketDTO) {
        this.tipoCobranca = ticketDTO.tipoCobranca() == null ?
                TipoCobranca.FLEXIVEL : TipoCobranca.valueOf(ticketDTO.tipoCobranca().toUpperCase());

        if (this.tipoCobranca.equals(TipoCobranca.FIXO)) {
            this.permanencia = ticketDTO.permanencia() == null ?
                    Duration.ofHours(1) : Duration.ofHours(Long.parseLong(ticketDTO.permanencia()));
        }
    }
}