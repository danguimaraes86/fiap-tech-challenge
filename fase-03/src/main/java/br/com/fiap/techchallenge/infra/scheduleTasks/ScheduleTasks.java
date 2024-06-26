package br.com.fiap.techchallenge.infra.scheduleTasks;

import br.com.fiap.techchallenge.entities.Condutor;
import br.com.fiap.techchallenge.entities.Ticket;
import br.com.fiap.techchallenge.infra.enums.TipoCobranca;
import br.com.fiap.techchallenge.services.CondutorService;
import br.com.fiap.techchallenge.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 30)
    public void notificarTicketsFlexiveis() {
        List<Ticket> ticketsFlexivel = ticketService.findTicketsAbertos(TipoCobranca.FLEXIVEL);

        ticketsFlexivel.forEach(ticket -> {
            if (ticket.getHorarioEntrada().until(LocalDateTime.now(), ChronoUnit.MINUTES) >= 50) {
                Condutor condutor = condutorService.findCondutorByCpf(ticket.getCondutor());
                condutor.notificar(ticket, mensagemTempoFlexivel);
            }
        });
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 30)
    public void notificarTicketsFixo() {
        List<Ticket> ticketsFixo = ticketService.findTicketsAbertos(TipoCobranca.FIXO);

        ticketsFixo.forEach(ticket -> {
            if (ticket.getHorarioEntrada().until(
                    LocalDateTime.now().plusMinutes(
                            ticket.getPermanencia()
                                    .minusMinutes(60).toMinutes()), ChronoUnit.MINUTES) >= 50) {
                Condutor condutor = condutorService.findCondutorByCpf(ticket.getCondutor());
                condutor.notificar(ticket, mensagemTempoFixo);
            }
        });
    }

}
