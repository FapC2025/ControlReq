package com.example.demo.service;

import com.example.demo.DTO.UsuarioDto;
import com.example.demo.entity.Usuarios;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUsuarios {

	Flux<Usuarios> listarUsuarios();
	Mono<Usuarios> login(String email);
	Mono<Usuarios> crearUsuario(Usuarios usuarios);
	Mono<Void> eliminarUsuario(String id);
}
