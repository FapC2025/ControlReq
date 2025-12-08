package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.entity.Documento;

import reactor.core.publisher.Flux;
import java.util.List;


public interface IDocumentoRepository extends ReactiveMongoRepository<Documento, String> {

	Flux<Documento> findByIdRequisicion(String idRequisicion);
}
