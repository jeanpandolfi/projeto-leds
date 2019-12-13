package com.example.projeto.leds.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		usuario.setSenha(this.converter(usuario.getSenha()));
		Usuario userSave = this.user.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}
	
	private String converter(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
		
	}
	

	
}
