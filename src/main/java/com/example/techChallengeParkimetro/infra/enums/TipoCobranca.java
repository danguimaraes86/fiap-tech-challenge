package com.example.techChallengeParkimetro.infra.enums;

import java.time.Duration;

public enum TipoCobranca {
    FIXO {
        public Double calcular(Duration permanencia) {
            double horaInicial = 20;
            double horaAdicional = permanencia.minusMinutes(60).toHours() * 10;
            return horaInicial + horaAdicional;
        }
    },
    FLEXIVEL {
        public Double calcular(Duration permanencia) {
            double valorHora = 15;
            return valorHora + (valorHora * permanencia.toHours());
        }
    };

    public abstract Double calcular(Duration permanencia);
}