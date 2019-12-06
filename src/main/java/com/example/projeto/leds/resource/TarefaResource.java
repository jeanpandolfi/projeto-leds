package com.example.projeto.leds.resource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.leds.event.RecursoCriadoEvent;
import com.example.projeto.leds.model.Tarefa;
import com.example.projeto.leds.repository.TarefaRepository;



@RestController
@RequestMapping("/tarefa")
public class TarefaResource {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	private List<Tarefa> listar(){
		return tarefaRepository.findAll();
	}
	
	@PostMapping
	private ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa , HttpServletResponse  response) {
		
		@Valid Tarefa categoriaSalva = this.tarefaRepository.save(tarefa);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void apagar(@PathVariable Long id) {
		this.tarefaRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa tarefa){
		
		return tarefaRepository.findById(id)
				.map(record -> {
					record.setDescricao(tarefa.getDescricao());
					record.setTitulo(tarefa.getTitulo());
					
					Tarefa tarefaUpdate = tarefaRepository.save(record);
					
					return ResponseEntity.ok().body(tarefaUpdate);
				
				}).orElseThrow(() -> new NoSuchElementException());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Optional<Tarefa> pessoaFind = this.tarefaRepository.findById(id);
		
		
		return !pessoaFind.isEmpty() ? ResponseEntity.ok(pessoaFind) : ResponseEntity.notFound().build();
	}
	
	
	@GetMapping("/pessoa/{id}")
	public ResponseEntity<?> findTarefaByPessoa(@PathVariable Long id){
		
		List<Tarefa> lista = this.tarefaRepository.findByPessoaPertencente_id(id);
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
		
	}
}
