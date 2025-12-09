package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Articulos")
public class Articulos {

	@Id
	private String id;
	private Integer cantidad;
	private String unidad;
	private String descripcion;
	private String marca;
	private String estatus;
	@CreatedDate
	private LocalDateTime  fechaCreacion;
}
