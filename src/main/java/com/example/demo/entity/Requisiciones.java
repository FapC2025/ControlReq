package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "Requisiciones")
public class Requisiciones {

	@Id
	private String id;
	private String folio;
	private String solicitante;
	private String prioridad;
	private String direccion;
	private String area;
	private String estatus;
	private String idusuario;
	private List<String> idArticulos;
	@CreatedDate
	private Date fechaCreacion;
	private Date fechaFinalizacion;
	
}
