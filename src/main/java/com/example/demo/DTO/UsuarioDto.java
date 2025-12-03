package com.example.demo.DTO;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.example.demo.entity.Comentarios;

public class UsuarioDto {
	@Id
	private String id;
	private String nombre;
	private String email;
	private String password;
	private String idUsuario;
	private List<Comentarios> comentariosDto;
	private String fechaCreacion;
}
