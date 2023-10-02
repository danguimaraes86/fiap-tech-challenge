package com.example.techChallengeParkimetro.entities;

import com.example.techChallengeParkimetro.infra.enums.FormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String nome;
    private String cpf;
    private String email;
    private String celular;
    private FormaPagamento formaPagamento;

    public Condutor(String nome, String cpf, String email, String celular, FormaPagamento formaPagamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.celular = celular;
        this.formaPagamento = formaPagamento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
