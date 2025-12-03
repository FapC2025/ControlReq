package com.example.demo.service;

import java.nio.file.Path;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Documento;
import com.example.demo.repository.IDocumentoRepository;

import java.nio.file.StandardCopyOption;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class DocumentoService implements IDocumento {
	@Value("${app.uploads.path}")
    private String uploadPath;

    @Value("${app.uploads.base-url}")
    private String baseUrl;

    private final IDocumentoRepository documentRepository;
    
    public DocumentoService(IDocumentoRepository documentRepository) {
    	this.documentRepository = documentRepository;
    }
    
    @Override
    public Mono<String> guardarDocumento(FilePart file) {
        Path folder = Path.of(uploadPath);

        return Mono.fromCallable(() -> {
                    // Crear carpeta si no existe
                    if (!Files.exists(folder)) {
                        Files.createDirectories(folder);
                    }
                    return folder;
                })
                .subscribeOn(Schedulers.boundedElastic()) // evita bloquear el event loop
                .flatMap(path -> {
                    Path destination = path.resolve(file.filename());
                    // transferTo devuelve un Mono<Void>, luego devolvemos la URL
                    return file.transferTo(destination)
                               .thenReturn(baseUrl + "/" + file.filename());
                });
    }

    public Mono<Documento> uploadFile(FilePart file,String idUsuario ,String idRequisicion) {
    	
        return guardarDocumento(file)
                .flatMap(url -> {
                    Documento doc = new Documento(
                    		file.filename(), 
                    		url,
                    		idRequisicion,
                    	
                    		idUsuario
                    		);
                    
                    return documentRepository.save(doc);
                });
    }
}
