package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Usuarios")
public class Usuarios {

	@Id
	private String id;
	private String nombre;
	private String email;
	private String password;
	private String Departamento;
	
	private String fechaCreacion;
}
