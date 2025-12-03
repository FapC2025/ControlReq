package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Usuarios;
import com.example.demo.service.UsuariosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/")
@Tag(name = "Usuario", description = "Operaciones relacionadas con el usuario")
public class UsuariosController {

	private final UsuariosService usuarioService;
	
	public UsuariosController(UsuariosService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping("/listarUsuarios")
	@Operation(summary="Operacion que obtienela lista de todos los usuarios registrados en la base de datos")
	Flux<Usuarios> listarUsuarios(){
		return usuarioService.listarUsuarios();
	}
	
	@PostMapping("/agregarUsuario")
	@Operation(summary = "Crea el documento necesario para registrar un usuario nuevo")
	Mono<Usuarios> crearUsuario(@RequestBody Usuarios usuarios){
		System.out.println("datos del usuario" + usuarios);
		return usuarioService.crearUsuario(usuarios);
	}
	
	@DeleteMapping("/eliminarUsuario/{id}")
	@Operation(summary="Operacion relacionada a la elimninación de un usuario usando el id")
	Mono<Void> eliminarUsuario(@PathVariable String id){
		return usuarioService.eliminarUsuario(id);
	}

}
