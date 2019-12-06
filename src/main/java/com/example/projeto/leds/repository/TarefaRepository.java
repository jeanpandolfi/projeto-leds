package com.example.projeto.leds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.leds.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

	List<Tarefa> findByPessoaPertencente_id(Long id);

}
