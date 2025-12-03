package com.example.demo.controller;

import java.awt.PageAttributes.MediaType;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DTO.RequisicionDto;
import com.example.demo.entity.Documento;
import com.example.demo.entity.Requisiciones;
import com.example.demo.service.DocumentoService;
import com.example.demo.service.RequisicionesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Tag(name = "Comentarios", description = "Operaciones relacionadas a los Comentarios")
public class DocumentoController {

private final DocumentoService documentoService;
	

	public DocumentoController(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}
	
	@PostMapping("/subirDocumento"
				
			)
	@Operation(summary="Subir documento")
	public Mono<Documento> crearRequisicion(@AuthenticationPrincipal @RequestPart("file") FilePart file, @RequestHeader("idUsuario") String idUsuario  ,@RequestPart("idRequisicion") String idRequisicion ){
		System.out.println("Datos de controller" + file);
	
		return documentoService.uploadFile(file,idUsuario,idRequisicion);
	}
}
