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
import com.example.projeto.leds.model.Pessoa;
import com.example.projeto.leds.model.Tarefa;
import com.example.projeto.leds.model.Usuario;
import com.example.projeto.leds.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoa")
public class PessoaResource {
	
	@Autowired
	private UsuarioResource usuarioResource;
	
	@Autowired
	private TarefaResource tarefaResource;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> listar(){
		return this.pessoaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Long id) {
		Optional<Pessoa> pessoaFind = this.pessoaRepository.findById(id);
		
		//Tratando se caso a pessoa não existir
		return pessoaFind.isPresent() ? ResponseEntity.ok(pessoaFind) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario, HttpServletResponse response){
		Pessoa novaPessoa = new Pessoa();
		novaPessoa.setEmail(usuario.getEmail());
		novaPessoa.setNome(usuario.getNome());
		
		Pessoa pessoaSalva = this.pessoaRepository.save(novaPessoa);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

		return this.usuarioResource.cadastrar(usuario, response);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		this.pessoaRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		
		return pessoaRepository.findById(id)
				.map(record -> {
					record.setEmail(pessoa.getEmail());
					record.setNome(pessoa.getNome());
					
					Pessoa pessoaUpdate = pessoaRepository.save(record);
					
					return ResponseEntity.ok().body(pessoaUpdate);
				
				}).orElseThrow(() -> new NoSuchElementException());
		
	}
	
	/*
	 * Listar Tarefas da Pessoa Logada
	 */
	@GetMapping("/tarefa")
	public ResponseEntity<List<Tarefa>> findTarefaByPessoa(@PathVariable Long id){
		
		return this.tarefaResource.findTarefaByPessoa(id);
				
	}
	
}
