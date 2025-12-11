package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ComentarioDTO;
import com.example.demo.entity.Comentarios;
import com.example.demo.repository.IComentariosRepository;
import com.example.demo.service.ComentariosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Tag(name = "Comentarios", description = "Operaciones relacionadas a los Comentarios")
public class ComentariosController {

	public final ComentariosService comentariosService;
	
	public ComentariosController (ComentariosService comentariosService) {
		this.comentariosService = comentariosService;
	}
	
	@PostMapping("/agregarComentarios")
	@Operation(summary="Esta operacion crea el documento que almacenara todos los comentarios que los usuarios generen")
	public Mono<Comentarios> agregarComentario (@RequestBody Comentarios comentarios){
		return comentariosService.agregarComentario(comentarios);
	}
	@DeleteMapping("/eliminarComentarios/{id}")
	@Operation(summary="Esta Operación se encarga de eliminar los comentarios usando el id del comentario")
	public Mono<Void> eliminarComentario(@PathVariable String id){
		return comentariosService.eliminarComentario(id);
		
	}
	@PatchMapping("/editarComentario/{id}")
	@Operation(summary="Operacion encargada de actualizar el comentario")
	public Mono<Comentarios> editarComentario(@PathVariable String id, @RequestBody Comentarios comentarios){
		return comentariosService.editarComentario(id, comentarios);
		
	}
	@GetMapping("/listarComentarios/usuario/{idUsuario}/requisicion/{idRequisicion}")
	@Operation(summary="Obtener todos los comantarios para el usuario y sus requisiciones")
	public Flux<Comentarios> listarComentarios(@PathVariable String idUsuario,@PathVariable String idRequisicion){
		return comentariosService.obtenerComentarios(idUsuario, idRequisicion);
	}
	@GetMapping("/comentarios/requisicion/{idRequisicion}")
	@Operation(summary="Obtener todos los comentarios con nombre de usuario")
	public Flux<ComentarioDTO> buscarComentario(@PathVariable String idRequisicion){
		return comentariosService.buscarComentarios( idRequisicion);
	}
}