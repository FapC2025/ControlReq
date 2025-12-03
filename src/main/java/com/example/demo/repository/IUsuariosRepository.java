package com.example.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.DTO.UsuarioDto;
import com.example.demo.entity.Usuarios;

import reactor.core.publisher.Mono;
import java.util.List;


public interface IUsuariosRepository extends ReactiveMongoRepository<Usuarios, String> {

	Mono<Usuarios>  findByEmail(String email);
}
