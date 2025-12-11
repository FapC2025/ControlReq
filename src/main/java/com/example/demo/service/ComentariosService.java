package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.DTO.ComentarioDTO;
import com.example.demo.DTO.RequisicionDto;
import com.example.demo.entity.Articulos;
import com.example.demo.entity.Comentarios;
import com.example.demo.entity.Requisiciones;
import com.example.demo.entity.Usuarios;
import com.example.demo.repository.IComentariosRepository;
import com.example.demo.repository.IUsuariosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ComentariosService implements IComentarios  {

    private final UsuariosService usuariosService;


	private final IComentariosRepository comentariosRepository;
	private final IUsuariosRepository usuarioRepository;
	public ComentariosService (IComentariosRepository comentariosRepository,IUsuariosRepository usuarioRepository, UsuariosService usuariosService) {
		this.comentariosRepository =comentariosRepository;
		this.usuarioRepository = usuarioRepository;
		this.usuariosService = usuariosService;
	}

	@Override
	public Mono<Comentarios> agregarComentario( Comentarios comentarios) {
		// TODO Auto-generated method stub
		comentarios.setFechaCreacion(LocalDate.now().toString());
		return comentariosRepository.save(comentarios);
	}

	@Override
	public Mono<Void> eliminarComentario(String id) {
		// TODO Auto-generated method stub
		return comentariosRepository.deleteById(id);
	}

	@Override
	public Mono<Comentarios> editarComentario(String id, Comentarios comentarios) {
		// TODO Auto-generated method stub
		return comentariosRepository.findById(id)
				.flatMap(comentariosExistentes ->{
					comentariosExistentes.setComentario(comentarios.getComentario());
					
					
					return comentariosRepository.save(comentariosExistentes);
				});
	}

	public Flux<Comentarios> obtenerComentarios(String idUsuario, String idRequisicion) {
		// TODO Auto-generated method stub
		  // Validación de IDs
        if (idUsuario == null || idUsuario.isEmpty()) {
            return Flux.error(new IllegalArgumentException("El idUsuario no puede estar vacío"));
        }

        if (idRequisicion == null || idRequisicion.isEmpty()) {
            return Flux.error(new IllegalArgumentException("El idRequisicion no puede estar vacío"));
        }
		return comentariosRepository.findByIdUsuarioAndIdRequisicion(idUsuario, idRequisicion);
	}

	@Override
	public Flux<ComentarioDTO> buscarComentarios(String idRequisiciones) {

	    return comentariosRepository.findByIdRequisicion(idRequisiciones)
	        .flatMap(comentario ->
	            usuarioRepository.findById(comentario.getIdUsuario())
	                .map(usuario -> mapToDto(comentario, usuario))
	        );
	}

	private ComentarioDTO mapToDto(Comentarios comentario, Usuarios user) {

	    ComentarioDTO dto = new ComentarioDTO();
	    dto.setId(comentario.getId());
	    dto.setIdUsuario(comentario.getIdUsuario());
	    dto.setUsuario(user.getNombre());
	    dto.setIdRequisicion(comentario.getIdRequisicion());
	    dto.setComentario(comentario.getComentario());
	    dto.setFechaCreacion(comentario.getFechaCreacion());

	    return dto;
	}


	
}
