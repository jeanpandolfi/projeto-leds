package com.example.projeto.leds.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.projeto.leds.model.Usuario;
import com.example.projeto.leds.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//System.out.println("Email vindo do cliente: " + email);
		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email); 
		
		//System.out.println("Usuário retornado do banco: "+ usuario.get().getEmail()+" "+usuario.get().getSenha());
		Usuario user = usuario.orElseThrow(() -> new UsernameNotFoundException("Usuário ou senha inválidos"));
		User us = new User(email, user.getSenha(), getPermissoes());
		System.out.println("User: "+us.getUsername()+" senha: "+us.getPassword() );
		return us;
	}

	private Collection<? extends GrantedAuthority> getPermissoes() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE"));
		return authorities;
	}
		
}
