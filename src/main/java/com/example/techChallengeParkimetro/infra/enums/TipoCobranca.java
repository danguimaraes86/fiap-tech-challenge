package com.example.techChallengeParkimetro.infra.enums;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum TipoCobranca {
    DIARIA{
        @Override
        public double executar(LocalDateTime horarioEntrada) {
            double valorInicialPrimeiroDia = 70;
            double precoDiaAdicional = 60;
            int contador = 0;
            double DiasPermanecido = horarioEntrada.until(LocalDateTime.now(), ChronoUnit.HOURS);

            if (DiasPermanecido / 24 > 1) {
                int horasPermanecidoDescontoDaPrimeiraHora = ((int) DiasPermanecido / 24)-1;

                contador = contador + 1 + horasPermanecidoDescontoDaPrimeiraHora;
            }

            return (valorInicialPrimeiroDia + (precoDiaAdicional * contador));
        }
    },
    PORHORA{
        @Override
        public double executar(LocalDateTime horarioEntrada) {
            double valorInicialPrimeiraHora = 15;
            double precoHoraAdicional = 10;
            int contador = 0;
            double horasPermanecido = horarioEntrada.until(LocalDateTime.now(), ChronoUnit.SECONDS);

            if (horasPermanecido / 60 > 1) {
                int horasPermanecidoDescontoDaPrimeiraHora = ((int) horasPermanecido / 60)-1;

                contador = contador + 1 + horasPermanecidoDescontoDaPrimeiraHora;
            }

            return (valorInicialPrimeiraHora + (precoHoraAdicional * contador));
        }
    };

    public abstract double executar(LocalDateTime horarioEntrada);
}