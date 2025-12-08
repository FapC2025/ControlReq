package com.example.demo.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Documento;
import com.example.demo.repository.IDocumentoRepository;

import java.nio.file.StandardCopyOption;

import reactor.core.publisher.Flux;
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

	@Override
	public Flux<Documento> listarDocumentos(String idRequisicion) {
		// TODO Auto-generated method stub
		return documentRepository.findByIdRequisicion(idRequisicion);
	}

	@Override
	public Mono<Void> extraerDocuemntos(String fileName, ServerHttpResponse response ) {
		// TODO Auto-generated method stub
		Path filePath = Paths.get(uploadPath, fileName);
        File file = filePath.toFile();

        if (!file.exists() || !file.canRead()) {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return Mono.empty();
        }

        // Determinar el tipo de contenido dinámicamente si es posible (ej. image/jpeg, application/pdf)
        // Usamos MediaType.APPLICATION_OCTET_STREAM como fallback genérico
        String contentType = "application/octet-stream"; 
        try {
            contentType = Files.probeContentType(filePath);
            if (contentType == null) contentType = "application/octet-stream";
        } catch (IOException e) {
            // Manejar error de lectura de tipo de archivo
        }

        // Si quieres que se muestre en el navegador (ej. PDFs o imágenes), usa INLINE
        // response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");

        // Si quieres forzar la descarga, usa ATTACHMENT
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
        response.getHeaders().setContentType(MediaType.parseMediaType(contentType));
        response.getHeaders().setContentLength(file.length());

        Flux<DataBuffer> fileStream = DataBufferUtils.read(
            filePath,
            new DefaultDataBufferFactory(),
            4096
        );

        return response.writeWith(fileStream);

	}
}
