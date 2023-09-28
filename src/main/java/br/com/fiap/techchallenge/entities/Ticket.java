package br.com.fiap.techchallenge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.util.UUID;
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID uuid;
    String nome;
    String cpf;
    String email;
    String celular;
    FormaPagamento formaPagamento;

    public Ticket(UUID uuid, String nome, String cpf, String email, String celular, FormaPagamento formaPagamento) {
        this.uuid = uuid;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.celular = celular;
        this.formaPagamento = formaPagamento;
    }
    public Ticket(){}

    public UUID getUuid() {
        return uuid;
    }

    public Ticket setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Ticket setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCpf() {
        return cpf;
    }

    public Ticket setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Ticket setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCelular() {
        return celular;
    }

    public Ticket setCelular(String celular) {
        this.celular = celular;
        return this;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public Ticket setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }
}

