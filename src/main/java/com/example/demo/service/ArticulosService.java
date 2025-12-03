package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Articulos;
import com.example.demo.repository.IArticulosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticulosService implements IArticulos {
	

	private final IArticulosRepository articulosRepository;

	public ArticulosService(IArticulosRepository articulosRepository) {
		// TODO Auto-generated constructor stub
		this.articulosRepository = articulosRepository;
	}
	
	@Override
	public Mono<Articulos> registrarArticulo(Articulos articulos) {
		// TODO Auto-generated method stub
		return this.articulosRepository.save(articulos);
	}

	@Override
	public Flux<Articulos> listaArticulos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> eliminarArticulo(String id) {
		// TODO Auto-generated method stub
		return articulosRepository.deleteById(id);
	}



}
