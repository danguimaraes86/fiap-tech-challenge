package com.example.techChallengerParkimetro.entities;

import com.example.techChallengerParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengerParkimetro.infra.enums.TipoCobranca;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID uuid;
    LocalDateTime horarioEntrada;
    LocalDateTime horarioSaida;
    TipoCobranca tipoCobranca;
    String permanencia;
    Double valorTotal;

    public Ticket(UUID uuid, LocalDateTime horarioEntrada, LocalDateTime horarioSaida, TipoCobranca tipoCobranca, Double valorTotal) {
        this.uuid = uuid;
        this.horarioEntrada = LocalDateTime.now();
        this.horarioSaida = horarioSaida;
        this.tipoCobranca = tipoCobranca;;
        this.valorTotal = valorTotal;
    }
    public Ticket(LocalDateTime horarioEntrada, TipoCobranca tipoCobranca) {
        this.horarioEntrada = LocalDateTime.now();
        this.tipoCobranca = tipoCobranca;
    }
    public Ticket(){}
    public Ticket (TicketDTO ticketDTO){
        this.horarioEntrada = ticketDTO.horarioEntrada();
        this.horarioSaida = ticketDTO.horarioSaida();
        this.tipoCobranca = ticketDTO.tipoCobranca();
        this.valorTotal = ticketDTO.valorTotal();
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }
    public Ticket setHorarioEntrada(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
        return this;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }
    public Ticket setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
        return this;
    }

    public TipoCobranca getTipoCobranca() {
        return tipoCobranca;
    }
    public Ticket setTipoCobranca(TipoCobranca tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
        return this;
    }

    public String getPermanencia() {
        return permanencia;
    }
    public Ticket setPermanencia(String permanencia) {
        this.permanencia = permanencia;
        return this;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
    public Ticket setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }
}

