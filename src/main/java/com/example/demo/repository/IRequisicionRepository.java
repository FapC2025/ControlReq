package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Requisiciones;

import reactor.core.publisher.Flux;

public interface IRequisicionRepository extends ReactiveMongoRepository<Requisiciones, String> {

}
