package br.com.fiap.techchallenge.enderecoInstalacao.controller;

import br.com.fiap.techchallenge.enderecoInstalacao.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<?> findAll(){

    }

    @GetMapping
    public {

    }

    @PostMapping
    public {

    }

    @PutMapping
    public {

    }

    @DeleteMapping
    public {

    }
}
