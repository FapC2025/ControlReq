package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.DTO.ComentarioDTO;
import com.example.demo.entity.Comentarios;

import reactor.core.publisher.Flux;

public interface IComentariosRepository extends ReactiveMongoRepository<Comentarios, String> {

	Flux<Comentarios> findByIdUsuarioAndIdRequisicion(String idUsuario,String idRequisicion);
	Flux<Comentarios> findByIdRequisicion(String idRequisicion);
}
