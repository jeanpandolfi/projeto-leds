package com.example.projeto.leds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.leds.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
