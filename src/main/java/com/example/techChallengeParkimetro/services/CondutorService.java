package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.entities.dtos.CondutorDTO;
import com.example.techChallengeParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengeParkimetro.infra.repositories.CondutorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class CondutorService {

    private final CondutorRepository condutorRepository;


    public CondutorService(CondutorRepository condutorRepository) {
        this.condutorRepository = condutorRepository;
    }

    private static Supplier<NoSuchElementException> getCondutorNaoEncontrado() {
        return () -> new NoSuchElementException("condutor não encontrado");
    }

    public List<Condutor> findAll() {
        return condutorRepository.findAll();
    }

    public Condutor findById(UUID id) {
        return condutorRepository.findById(id).orElseThrow(getCondutorNaoEncontrado());
    }

    public Condutor findCondutorByCpf(String cpf) {
        return condutorRepository.findCondutorByCpf(cpf).orElseThrow(getCondutorNaoEncontrado());
    }

    public Condutor create(Condutor condutor) {
        try {
            condutor.limparCpfCelular();
            return condutorRepository.save(condutor);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("cpf já cadastrado");
        }
    }

    public void delete(String cpf) {
        condutorRepository.delete(findCondutorByCpf(cpf));
    }

    public Condutor update(String cpf, CondutorDTO condutorDTO) {
        Condutor condutorByCpf = findCondutorByCpf(cpf);
        condutorByCpf.update(condutorDTO);
        return condutorRepository.save(condutorByCpf);
    }

    public void vincularVeiculo(String condutorCpf, Veiculo veiculo) {
        Condutor condutor = findCondutorByCpf(condutorCpf);
        condutor.vincularVeiculo(veiculo);
        condutorRepository.save(condutor);
    }

    public List<Condutor> findByAtributo(HashMap<String, String> params) {
        return condutorRepository.findCondutorByCpfOrNomeIgnoreCaseOrEmail(
                params.get("cpf"), params.get("nome"), params.get("email")
        ).orElseThrow();
    }

    public void vincularTicket(Condutor condutor, Ticket ticktCreated) {
        condutor.vincularTicket(ticktCreated);
        condutorRepository.save(condutor);
    }

    public List<Ticket> buscarTickets(String condutorCPF) {
        var condutor = findCondutorByCpf(condutorCPF);

        return condutor.getTickets();
    }
}
