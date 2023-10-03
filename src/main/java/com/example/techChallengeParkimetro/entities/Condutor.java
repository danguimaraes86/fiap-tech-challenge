package com.example.techChallengeParkimetro.entities;

import com.example.techChallengeParkimetro.entities.dtos.CondutorDTO;
import com.example.techChallengeParkimetro.infra.enums.FormaPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String nome;
    @NotNull
    @CPF(message = "CPF inválido")
    private String cpf;
    @NotNull
    @Email
    private String email;
    private String celular;
    @NotNull
    @Enumerated(EnumType.STRING)
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

    public CondutorDTO toDTO() {
        return new CondutorDTO(this.nome, this.cpf, this.email, this.celular, this.formaPagamento.toString());
    }
}
