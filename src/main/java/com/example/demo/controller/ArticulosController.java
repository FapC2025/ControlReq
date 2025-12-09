package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Articulos;
import com.example.demo.service.ArticulosService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Tag(name = "Articulos", description = "Operaciones relacionadas con los articulos")
public class ArticulosController {
	
	private final ArticulosService articulosService;
	
	public ArticulosController(ArticulosService articulosService) {
		this.articulosService = articulosService;
	}
	
	
	@PostMapping("/agregarArticulo")
	@Operation(summary = "Se usa para generar el documento y agregar los articulos")
	public Mono<Articulos> registrarArticulo(Articulos articulos){
		return articulosService.registrarArticulo(articulos);
	}
	
	@GetMapping("/listarArticulo")
	@Operation(summary = "Se usa para consulta el documento y traer todos los articulos")
	public Flux<Articulos> listaArticulos(){
		return articulosService.listaArticulos();
	}
	@DeleteMapping("/eliminarArticulo/{id}")
	@Operation(summary="ELimina el articulo en base al id")
	public Mono<Void> eliminarRequisicion(@PathVariable String id) {
		return  articulosService.eliminarArticulo(id);
	}
	@PatchMapping("/actualizarArticulo/{id}")
	@Operation(summary="Operacion referente a la actualización del articulo. ")
	public Mono<Articulos> actualizarArticulo(@PathVariable String id, @RequestBody String estatus){
		return articulosService.actualizarEstado(id, estatus);
	}
}
