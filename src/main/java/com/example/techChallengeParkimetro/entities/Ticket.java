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

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private Double valorTotal;
    @NotNull
    private String veiculo;
    @NotNull
    private String condutor;

    public Ticket(
            String condutor,
            String veiculo,
            LocalDateTime horarioEntrada,
            TipoCobranca tipoCobranca
    ) {
        this.condutor = condutor;
        this.veiculo = veiculo;
        this.horarioEntrada = horarioEntrada;
        this.tipoCobranca = tipoCobranca;
    }

    public void registarHorarioSaida() {
        this.horarioSaida = LocalDateTime.now();
        calcularValorTotal();
    }

    public void calcularValorTotal() {
        this.valorTotal = this.tipoCobranca.executar(this.horarioEntrada, this.horarioSaida);
    }

    public String calcularPermanencia(TipoCobranca tipoCobranca) {
        if (tipoCobranca == TipoCobranca.PORHORA)
            return LocalTime.ofSecondOfDay(this.horarioEntrada.until(this.horarioSaida, ChronoUnit.SECONDS)) + " - Hrs : Min : Seg";

        return (this.horarioEntrada.until(this.horarioSaida, ChronoUnit.DAYS) + 1) + " Diarias";
    }

    public TicketDTO toDTO() {
        String permanencia = this.horarioSaida == null ? Ticket.VAZIO : this.calcularPermanencia(this.tipoCobranca);

        return new TicketDTO(this.id, this.horarioEntrada, this.horarioSaida, this.tipoCobranca.name(),
                permanencia, this.valorTotal, this.condutor, this.veiculo
        );
    }

    public Boolean isEmAberto() {
        return horarioSaida == null;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", horarioEntrada=" + horarioEntrada +
                ", horarioSaida=" + horarioSaida +
                ", tipoCobranca=" + tipoCobranca.name() +
                ", valorTotal=" + valorTotal +
                ", veiculo='" + veiculo + '\'' +
                ", condutor='" + condutor + '\'' +
                '}';
    }
}