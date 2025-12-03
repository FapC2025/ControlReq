package com.example.demo.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.DTO.RequisicionDto;
import com.example.demo.entity.Articulos;
import com.example.demo.entity.Requisiciones;
import com.example.demo.repository.IArticulosRepository;
import com.example.demo.repository.IRequisicionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequisicionesService implements IRequisiciones {
	private final IRequisicionRepository requisicionesrepository;
	private final IArticulosRepository articulorepository;

	public RequisicionesService(IRequisicionRepository requisicionesrepository,
			IArticulosRepository articulorepository) {
		this.requisicionesrepository = requisicionesrepository;
		this.articulorepository = articulorepository;
	}

	@Override
	public Mono<Requisiciones> crearRequisicion(RequisicionDto dto) {
		// TODO Auto-generated method stub
		if (dto.getArticulosDto() == null) {
			return Mono.error(new IllegalArgumentException("La lista de artículos no puede ser null"));
		}
		return articulorepository.saveAll(dto.getArticulosDto()).collectList() // Obtener lista guardada con IDs
				.flatMap(articulosGuardados -> {
					// 2. Extraer IDs de los artículos guardados
					List<String> ids = articulosGuardados.stream().map(Articulos::getId).collect(Collectors.toList());

					// 3. Crear requisición con IDs
					Requisiciones req = new Requisiciones();
					req.setId(dto.getIdDto());
					req.setFolio(dto.getFolioDto());
					req.setSolicitante(dto.getSolicitanteDto());
					req.setPrioridad(dto.getPrioridadDto());
					req.setDireccion(dto.getDireccionDto());
					req.setArea(dto.getAreaDto());
					req.setEstatus(dto.getEstatusDto());
					req.setIdusuario(dto.getIdUsuario());
					req.setIdArticulos(ids);
					//req.setFechaCreacion(new Date());

					// 4. Guardar requisición
					System.out.println("service" + req);
					return requisicionesrepository.save(req);
				});
	}

	  @Override
	    public Flux<RequisicionDto> listRequisiciones() {
	        return requisicionesrepository.findAll()
	        		 .flatMap(requisicion ->
	        		 articulorepository.findAllById(requisicion.getIdArticulos())
	                     .collectList() // ← esto da Mono<List<Articulos>>
	                     .map(articulos -> mapToDto(requisicion, articulos)) // ← esto da RequisicionDto
	                     .cast(RequisicionDto.class) // ← asegura el tipo si hace falta
	             );
	    }

	    private RequisicionDto mapToDto(Requisiciones r, List<Articulos> articulos) {
	        RequisicionDto dto = new RequisicionDto();
	        dto.setIdDto(r.getId());
	        dto.setFolioDto(r.getFolio());
	        dto.setSolicitanteDto(r.getSolicitante());
	        dto.setPrioridadDto(r.getPrioridad());
	        dto.setDireccionDto(r.getDireccion());
	        dto.setAreaDto(r.getArea());
	        dto.setEstatusDto(r.getEstatus());
	        dto.setArticulosDto(articulos); // ya son entidades completas
	        dto.setFechaCreacion(r.getFechaCreacion().toString());
	        
	        return dto;
	    }

	@Override
	public Mono<Void> eliminarRequisicion(String id) {
		return requisicionesrepository.deleteById(id);
	}
	
	@Override
	public Mono<RequisicionDto> buscarRequisicion(String id){
		return requisicionesrepository.findById(id)
       		 .flatMap(requisicion ->
       		 articulorepository.findAllById(requisicion.getIdArticulos())
                    .collectList() // ← esto da Mono<List<Articulos>>
                    .map(articulos -> mapToDto(requisicion, articulos)) // ← esto da RequisicionDto
                    .cast(RequisicionDto.class) // ← asegura el tipo si hace falta
            );
	}
	@Override
	public Mono<Requisiciones> actualizarRequiscision( String id,  String estatus){
		
	
		if(estatus.equals("En proceso")) {
			return requisicionesrepository.findById(id)
					.flatMap(requisiciones ->{
						requisiciones.setEstatus(estatus);		
						return requisicionesrepository.save(requisiciones);
					});
		}
		Date fechaFinalizacion = Date.from(Instant.now());
		return requisicionesrepository.findById(id)
				.flatMap(requisiciones ->{
					requisiciones.setEstatus(estatus);
					requisiciones.setFechaFinalizacion(fechaFinalizacion);
					
					return requisicionesrepository.save(requisiciones);
				});
	}

}
