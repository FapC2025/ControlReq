package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.UsuarioDto;
import com.example.demo.entity.Usuarios;
import com.example.demo.repository.IUsuariosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuariosService implements IUsuarios {

	private IUsuariosRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UsuariosService(IUsuariosRepository usuarioRepository,PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		
	}
	@Override
	public Flux<Usuarios> listarUsuarios() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}
	///para login
	@Override
	public Mono<Usuarios> login(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail (email);
	}
	@Override
	public Mono<Usuarios> crearUsuario(Usuarios usuarios) {
		// TODO Auto-generated method stub

		usuarios.setFechaCreacion(LocalDate.now().toString());
		usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));
		return usuarioRepository.save(usuarios);
	}
	@Override
	public Mono<Void> eliminarUsuario(String id) {
		// TODO Auto-generated method stub
		return usuarioRepository.deleteById(id);
	}


}
