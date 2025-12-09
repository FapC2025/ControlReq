package com.example.demo.service;

import com.example.demo.DTO.RequisicionDto;
import com.example.demo.entity.Requisiciones;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRequisiciones  {

	Mono<Requisiciones> crearRequisicion(RequisicionDto dto);
	Flux<RequisicionDto>  listRequisiciones();
	Mono<Void> eliminarRequisicion(String id);
	Mono<RequisicionDto> buscarRequisicion(String id);
	Mono<Requisiciones> actualizarRequisicionCompleta(String id,Requisiciones req );
	Mono<Requisiciones> actualizarRequiscision(String id, String estatus);
}
