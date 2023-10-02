package com.example.techChallengeParkimetro.infra.repositories;

import com.example.techChallengeParkimetro.entities.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CondutorRepository extends JpaRepository<Condutor, UUID> {
}
