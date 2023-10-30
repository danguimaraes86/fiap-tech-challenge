package com.example.techChallengeParkimetro.infra.scheduleTasks;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.infra.enums.TipoCobranca;
import com.example.techChallengeParkimetro.services.CondutorService;
import com.example.techChallengeParkimetro.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleTasks {
    private final TicketService ticketService;
    private final CondutorService condutorService;

    private final String mensagemTempoFixo = "Prezado %s, o seu ticket iniciado em %tT irá expirar em breve. " +
            "Caso deseje, adicione mais tempo ao seu estacionamento.";
    private final String mensagemTempoFlexivel = "Prezado %s, o seu ticket iniciado em %tT será acrescido de mais 1h em breve. " +
            "Caso deseje, dirija-se ao pagamento para fechar seu estacionamento.";

    @Autowired
    public ScheduleTasks(TicketService ticketService, CondutorService condutorService) {
        this.ticketService = ticketService;
        this.condutorService = condutorService;
    }

    @Scheduled(cron = "0 50 * * * *")
    public void buscarTicketsAbertos() {
        System.out.println("------ cron rodando ------");
        List<Ticket> ticketsAbertos = ticketService.findTicketsAbertos();

        System.out.println("------ tickets FLEXÍVEL ------");
        List<Ticket> ticketsFlexivel = ticketsAbertos.stream()
                .filter(ticket -> ticket.getTipoCobranca().equals(TipoCobranca.FLEXIVEL)).toList();
        ticketsFlexivel.forEach(ticket -> {
            Condutor condutor = condutorService.findCondutorByCpf(ticket.getCondutor());
            condutor.notificar(ticket, mensagemTempoFlexivel);
        });

        System.out.println("------ tickets FIXO ------");
        List<Ticket> ticketsFixo = ticketsAbertos.stream()
                .filter(ticket -> ticket.getTipoCobranca().equals(TipoCobranca.FIXO)).toList();
        ticketsFixo.forEach(ticket -> {
            Condutor condutor = condutorService.findCondutorByCpf(ticket.getCondutor());
            condutor.notificar(ticket, mensagemTempoFixo);
        });
    }
}
