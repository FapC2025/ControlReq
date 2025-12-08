package com.example.demo.service;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Documento;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDocumento {

	Mono<String> guardarDocumento(FilePart file);
	Mono<Documento> uploadFile(FilePart file,String idUsuario ,String idRequisicion);
	Flux<Documento> listarDocumentos(String idRequisicion );
	Mono<Void> extraerDocuemntos(String fileName, ServerHttpResponse response );
}
