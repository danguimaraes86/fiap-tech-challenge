package com.example.techChallengerParkimetro.infra.enums;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum TipoCobranca {
    FIXO{
        @Override
        public double executar(LocalDateTime horarioEntrada, LocalDateTime horarioSaida) {
            double diferencaEntradaSaida = horarioEntrada.until(LocalDateTime.now(), ChronoUnit.MINUTES);

            return diferencaEntradaSaida;
        }
    },
    PORHORA{
        @Override
        public double executar(LocalDateTime horarioEntrada, LocalDateTime horarioSaida) {
            double diferencaEntradaSaida = horarioEntrada.until(horarioSaida, ChronoUnit.MINUTES);

            return diferencaEntradaSaida;
        }
    };

    public abstract double executar(LocalDateTime horarioEntrada, LocalDateTime horarioSaida);
}
