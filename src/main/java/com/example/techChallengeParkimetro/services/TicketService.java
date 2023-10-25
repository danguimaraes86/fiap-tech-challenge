package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengeParkimetro.infra.enums.TipoCobranca;
import com.example.techChallengeParkimetro.infra.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CondutorService condutorService;
    private final VeiculoService veiculoService;

    @Autowired
    public TicketService(
            TicketRepository ticketRepository,
            CondutorService condutorService,
            VeiculoService veiculoService
    ) {
        this.ticketRepository = ticketRepository;
        this.condutorService = condutorService;
        this.veiculoService = veiculoService;
    }

    public Page<TicketDTO> findAllTicket(Pageable pageable) {
        Page<Ticket> tickets = ticketRepository.findAll(pageable);
        return tickets.map(Ticket::toDTO);
    }

    public TicketDTO findById(UUID uuid) {
        Ticket ticket = ticketRepository.findById(uuid).orElseThrow(
                () -> new NoSuchElementException("Ticket não encontrado"));
        return ticket.toDTO();
    }

    public TicketDTO registarEntrada(TicketDTO ticketDTO) {
        Condutor condutor = condutorService.findCondutorByCpf(ticketDTO.condutorCpf());
        Veiculo veiculo = veiculoService.findByPlaca(ticketDTO.placaVeiculo());
        if (!condutor.getVeiculoList().contains(veiculo)) {
            throw new NoSuchElementException("veículo não vinculado ao condutor");
        }

        if (ticketRepository.findTicketByCondutorAndVeiculoIgnoreCaseAndHorarioSaidaIsNull(
                condutor.getCpf(), veiculo.getPlaca()).isPresent()) {
            throw new DataIntegrityViolationException("condutor ou veículo com ticket em aberto");
        }

        TipoCobranca tipoCobranca = ticketDTO.tipoCobranca() == null ?
                TipoCobranca.PORHORA : TipoCobranca.valueOf(ticketDTO.tipoCobranca().toUpperCase());

        Ticket ticktCreated = ticketRepository.save(
                ticketDTO.toEntity(condutor.getCpf(), veiculo.getPlaca(), LocalDateTime.now(), tipoCobranca));

        condutorService.vincularTicket(condutor, ticktCreated);
        return ticktCreated.toDTO();

    }

    public TicketDTO registrarSaida(TicketDTO ticketDTO) {
        Condutor condutor = condutorService.findCondutorByCpf(ticketDTO.condutorCpf());
        Veiculo veiculo = veiculoService.findByPlaca(ticketDTO.placaVeiculo());

        if(!condutor.getFormaPagamento().executarPagamento()) {
            throw new RuntimeException("Pagamento negado");
        }

        if (!condutor.getVeiculoList().contains(veiculo)) {
            throw new NoSuchElementException("veículo não vinculado ao condutor");
        }

        Ticket ticket = ticketRepository.findTicketByCondutorAndVeiculoIgnoreCaseAndHorarioSaidaIsNull(
                condutor.getCpf(), veiculo.getPlaca()).orElseThrow(
                () -> new NoSuchElementException("ticket não encontrado")
        );

        System.out.println(condutor.getFormaPagamento().getModoPagamento());

        ticket.registarHorarioSaida();
        return ticketRepository.save(ticket).toDTO();
    }


    public TicketDTO emitirRecibo(UUID uuid) {
        TicketDTO ticket = findById(uuid);
        if (ticket.isEmAberto())
            throw new NoSuchElementException("ticket em aberto");

        return ticket;
    }
}
