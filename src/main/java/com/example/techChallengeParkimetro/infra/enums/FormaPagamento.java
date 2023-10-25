package com.example.techChallengeParkimetro.infra.enums;

import java.util.Random;

public enum FormaPagamento {
    DEBITO ("Pago via Debito"){
        @Override
        public boolean executarPagamento() {

            return new Random().nextBoolean();
        }
    },
    CREDITO("Pago via Credito") {
        @Override
        public boolean executarPagamento() {

            return new Random().nextBoolean();
        }
    },
    PIX ("Pago via PIX"){
        @Override
        public boolean executarPagamento() {
            return true;
        }
    };
    private String modoPagamento;

    FormaPagamento(String formaPagamento) {
        this.modoPagamento = formaPagamento;
    }

    public String getModoPagamento() {
        return modoPagamento;
    }

    public abstract boolean executarPagamento();
}
