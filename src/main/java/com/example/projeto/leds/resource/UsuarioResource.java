package com.example.projeto.leds.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.leds.model.Usuario;
import com.example.projeto.leds.repository.UsuarioRepository;

@RestController
@RequestMapping("/user")
public class UsuarioResource {
	@Autowired
	private UsuarioRepository user;
	
	@PutMapping
	public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario pessoa, HttpServletResponse response) {
		pessoa.setSenha(this.converter(pessoa.getSenha()));
		Usuario user = this.user.save(pessoa);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	private String converter(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
		
	}
	
}
