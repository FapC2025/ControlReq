package com.example.demo.DTO;

import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.Data;
@Data
public class ComentarioDTO {
	@Id
	private String id;
	private String idUsuario;
	private String usuario;
	private String idRequisicion;	
	private String comentario;

	private String fechaCreacion;
}
