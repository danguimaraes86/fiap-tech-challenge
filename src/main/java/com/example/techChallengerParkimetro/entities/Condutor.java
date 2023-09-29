package com.example.techChallengerParkimetro.entities;

import com.example.techChallengerParkimetro.infra.enums.FormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
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
