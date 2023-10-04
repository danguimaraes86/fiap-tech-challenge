package com.example.techChallengeParkimetro.entities;

import com.example.techChallengeParkimetro.entities.dtos.CondutorDTO;
import com.example.techChallengeParkimetro.infra.enums.FormaPagamento;
import com.example.techChallengeParkimetro.utils.StringSanitizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
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

    public CondutorDTO toDTO() {
        return new CondutorDTO(this.id.toString(), this.nome, this.cpf, this.email, this.celular, this.formaPagamento.toString().toLowerCase());
    }

    public void update(CondutorDTO condutorDTO) {
        if (condutorDTO.nome() != null)
            this.nome = condutorDTO.nome();
        if (condutorDTO.cpf() != null)
            this.cpf = condutorDTO.cpf();
        if (condutorDTO.email() != null)
            this.email = condutorDTO.email();
        if (condutorDTO.celular() != null)
            this.celular = condutorDTO.celular();
        if (condutorDTO.formaPagamento() != null)
            this.formaPagamento = FormaPagamento.valueOf(condutorDTO.formaPagamento().toUpperCase());
    }

    public void limparCpfCelular() {
        this.cpf = StringSanitizer.somenteNumeros(cpf);
        this.celular = StringSanitizer.somenteNumeros(celular);
    }
}
