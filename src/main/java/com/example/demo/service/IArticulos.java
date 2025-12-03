package com.example.demo.service;

import com.example.demo.entity.Articulos;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IArticulos {

	Mono<Articulos> registrarArticulo(Articulos articulos);
	Flux<Articulos> listaArticulos();
	Mono<Void> eliminarArticulo(String id);
	
}
