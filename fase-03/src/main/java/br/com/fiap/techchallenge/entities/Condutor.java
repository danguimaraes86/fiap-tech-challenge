package br.com.fiap.techchallenge.entities;

import br.com.fiap.techchallenge.entities.dtos.CondutorDTO;
import br.com.fiap.techchallenge.infra.enums.FormaPagamento;
import br.com.fiap.techchallenge.utils.StringSanitizer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String nome;
    @NotNull
    @CPF(message = "CPF inválido")
    @Column(unique = true)
    private String cpf;
    @NotNull
    @Email
    private String email;
    private String celular;
    @NotNull
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    @OneToMany
    private List<Veiculo> veiculoList = new ArrayList<>();
    @OneToMany
    private List<Ticket> tickets = new ArrayList<>();

    public Condutor(String nome, String cpf, String email, String celular, FormaPagamento formaPagamento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.celular = celular;
        this.formaPagamento = formaPagamento;
    }

    public CondutorDTO toDTO() {
        return new CondutorDTO(
                this.nome, this.cpf, this.email, this.celular,
                this.formaPagamento.toString().toLowerCase(),
                this.veiculoList.stream().map(Veiculo::toDTO).toList());
    }

    public void update(CondutorDTO condutorDTO) {
        if (condutorDTO.nome() != null)
            this.nome = condutorDTO.nome();
        if (condutorDTO.cpf() != null)
            this.cpf = condutorDTO.cpf();
        if (condutorDTO.email() != null)
            this.email = condutorDTO.email();
        if (condutorDTO.celular() != null)
            this.celular = condutorDTO.celular();
        if (condutorDTO.formaPagamento() != null)
            this.formaPagamento = FormaPagamento.valueOf(condutorDTO.formaPagamento().toUpperCase());
    }

    public void vincularVeiculo(Veiculo veiculo) {
        this.veiculoList.add(veiculo);
    }

    public void limparCpfCelular() {
        this.cpf = StringSanitizer.somenteNumeros(cpf);
        if (this.celular != null)
            this.celular = StringSanitizer.somenteNumeros(celular);
    }

    public void vincularTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void notificar(Ticket ticket, String mensagem) {
        System.out.println(mensagem.formatted(this.nome, ticket.getHorarioEntrada()));
    }
}
