package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.entity.Documento;

public interface IDocumentoRepository extends ReactiveMongoRepository<Documento, String> {

}
