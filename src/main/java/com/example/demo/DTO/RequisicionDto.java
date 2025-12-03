package com.example.demo.DTO;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Articulos;

import lombok.Data;

@Data
public class RequisicionDto {

	private String idDto;
	private String folioDto;
	private String solicitanteDto;
	private String prioridadDto;
	private String direccionDto;
	private String areaDto;
	private String estatusDto;
	private String idUsuario;
	private List<Articulos> articulosDto;
	private String fechaCreacion;
	private String fechaFinalizacion;

	

}
