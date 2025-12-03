package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.entity.Articulos;

import reactor.core.publisher.Flux;

public interface IArticulosRepository extends ReactiveMongoRepository<Articulos, String> {
	Flux<Articulos> findAllById(Iterable<String> ids);
}
