package com.example.techChallengeParkimetro.infra.enums;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum TipoCobranca {
    DIARIA {
        @Override
        public Double executar(LocalDateTime horarioEntrada, LocalDateTime horarioSaida) {
            double valorInicialPrimeiroDia = 70;
            double precoDiaAdicional = 60;
            int contador = 0;
            double DiasPermanecido = horarioEntrada.until(horarioSaida, ChronoUnit.HOURS);

            if (DiasPermanecido / 24 > 1) {
                int horasPermanecidoDescontoDaPrimeiraHora = ((int) DiasPermanecido / 24) - 1;

                contador = contador + 1 + horasPermanecidoDescontoDaPrimeiraHora;
            }

            return (valorInicialPrimeiroDia + (precoDiaAdicional * contador));
        }

        @Override
        public void notificar(LocalDateTime horarioDeEntrada) {

            var diferencaHEntradaHSaida = horarioDeEntrada.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        }
    },
    PORHORA {
        @Override
        public Double executar(LocalDateTime horarioEntrada, LocalDateTime horarioSaida) {
            double valorInicialPrimeiraHora = 15;
            double precoHoraAdicional = 10;
            int contador = 0;
            double horasPermanecido = horarioEntrada.until(horarioSaida, ChronoUnit.SECONDS);

            if (horasPermanecido / 60 > 1) {
                int horasPermanecidoDescontoDaPrimeiraHora = ((int) horasPermanecido / 60) - 1;

                contador = contador + 1 + horasPermanecidoDescontoDaPrimeiraHora;
            }

            return (valorInicialPrimeiraHora + (precoHoraAdicional * contador));
        }

        @Override
        public void notificar(LocalDateTime horarioDeEntrada) {
            String notificacao = "";
            double diferencaHEntradaHSaida = horarioDeEntrada.until(LocalDateTime.now(), ChronoUnit.SECONDS);
            double faltaParaCompletarProximoPeriodo = diferencaHEntradaHSaida % 60;

            System.out.println(faltaParaCompletarProximoPeriodo);

            if (faltaParaCompletarProximoPeriodo / 60 >= 0.9  && faltaParaCompletarProximoPeriodo / 60 < 0.916){
                notificacao = "Falta 5 minutos para cobrança da proxima hora";
                System.out.println(notificacao);
            } else if (faltaParaCompletarProximoPeriodo / 60 >= 0.815  && faltaParaCompletarProximoPeriodo / 60 < 0.833) {
                notificacao = "Falta 10 minutos para cobrança da proxima hora";
                System.out.println(notificacao);
            }
        }
    };

    public abstract Double executar(LocalDateTime horarioEntrada, LocalDateTime horarioSaida);
    public abstract void notificar(LocalDateTime horarioDeEntrada);
    /* Em um cenario Pratico, este metodo deveria chamar uma requisição REST para o frontEnd, que
    lançasse para o usuario uma notificaçao nos periodos determinados na logica
     */
}