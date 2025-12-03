package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Documentos")
public class Documento {

	@Id
	private String id;
	private String idRequisicion;
	private String idUsuario;
	private String url;
	private String fileName;
	private Date fechaCreacion;
	
	  public Documento(String fileName, String url, String idRequisicion2, String idUsuario2) {
	        this.fileName = fileName;
	        this.url = url;
	        this.idUsuario = idUsuario2;
	        this.idRequisicion = idRequisicion2;
	
	        this.fechaCreacion = new  Date();
	    }
}
