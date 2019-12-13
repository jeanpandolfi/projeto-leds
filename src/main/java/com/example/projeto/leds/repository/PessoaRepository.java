package com.example.projeto.leds.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.leds.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	public Optional<Pessoa> findByEmail(String email);
	
}
