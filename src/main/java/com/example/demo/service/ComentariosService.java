package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Comentarios;
import com.example.demo.repository.IComentariosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ComentariosService implements IComentarios  {


	private final IComentariosRepository comentariosRepository;
	
	public ComentariosService (IComentariosRepository comentariosRepository) {
		this.comentariosRepository =comentariosRepository;
	}

	@Override
	public Mono<Comentarios> agregarComentario( Comentarios comentarios) {
		// TODO Auto-generated method stub
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

	
}
