package br.com.fiap.techchallenge.entities;

import br.com.fiap.techchallenge.infra.enums.FormaPagamento;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Condutor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID uuid;
    String nome;
    String cpf;
    String email;
    String celular;
    FormaPagamento formaPagamento;

}
