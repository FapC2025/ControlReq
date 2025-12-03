package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Comentarios")
public class Comentarios {

	@Id
	private String id;
	private String idUsuario;
	private String idRequisicion;	
	private String comentario;
	@CreatedDate
	private Date fechaCreacion;
}
