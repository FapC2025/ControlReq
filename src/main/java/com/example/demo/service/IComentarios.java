package com.example.demo.service;

import com.example.demo.DTO.ComentarioDTO;
import com.example.demo.entity.Comentarios;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IComentarios {

	Mono<Comentarios> agregarComentario(Comentarios comentarios);
	Mono<Void> eliminarComentario(String id);
	Mono<Comentarios> editarComentario(String id, Comentarios comentarios);
	Flux<ComentarioDTO>buscarComentarios( String idRequisicion);

}
