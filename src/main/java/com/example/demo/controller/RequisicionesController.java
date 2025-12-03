package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.RequisicionDto;
import com.example.demo.entity.Requisiciones;
import com.example.demo.repository.IRequisicionRepository;
import com.example.demo.service.RequisicionesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/")
@Tag(name = "Requisiciones", description = "Operaciones relacionadas con las requisiciones")
public class RequisicionesController {
	
	private final RequisicionesService requisicionesService;
	

	public RequisicionesController(RequisicionesService requisicionesService) {
		this.requisicionesService = requisicionesService;
	}
	
	@PostMapping("/agregarRequisicion")
	@Operation(summary="Crea el documento para agregar la informacion de las requisiciones")
	public Mono<Requisiciones> crearRequisicion(@AuthenticationPrincipal @RequestBody RequisicionDto dto ){
		System.out.println("Datos de controller" + dto);
		return requisicionesService.crearRequisicion(dto);
	}
	
	@GetMapping("/listRequisiciones")
	@Operation(summary="Lista toda la informacion de las requisiciones")
	public Flux<RequisicionDto> listRequisiciones(){
		return requisicionesService.listRequisiciones();
	}
	@GetMapping("/buscarRequisicion/{id}")
	@Operation(summary="Busca una sola requisicion")
	public Mono<RequisicionDto> buscarRequisicion(@PathVariable String id){
		return requisicionesService.buscarRequisicion(id);
	}
	
	
	@DeleteMapping("/eliminarRequisicion/{id}")
	@Operation(summary="ELimina una requisicion en base al id")
	public Mono<Void> eliminarRequisicion(@PathVariable String id) {
		return  requisicionesService.eliminarRequisicion(id);
	}
	
	@PatchMapping("/actualizacionParcial/{id}")
	@Operation(summary ="Solo actualiza el estatus y la fecha de finalizacion")
	public Mono<Requisiciones> actualizarRequisicion(@PathVariable String id, @RequestBody  String estatus ){
		return requisicionesService.actualizarRequiscision(id, estatus);
	}

}
