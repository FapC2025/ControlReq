package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.JWT.JWTUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.Usuarios;
import com.example.demo.service.AuthResponse;
import com.example.demo.service.UsuariosService;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth", description = "Operaciones relacionadas Para iniciar secion")
public class AuthController {

	 @Autowired
	    private JWTUtil jwtUtil;

	    @Autowired
	    private UsuariosService userService;
	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @PostMapping("/login")
	    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
	        return userService.login(authRequest.getEmail())
	            .flatMap(user -> {
	                if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
	                    Map<String, Object> claims = Map.of(
	                        "id", user.getId(),
	                        "nombre", user.getNombre(),
	                        "departamento", user.getDepartamento()
	                    );
	                    String token = jwtUtil.generateToken(user.getEmail(), claims);
	                    return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
	                } else {
	                    return Mono.error(new BadCredentialsException("Invalid username or password"));
	                }
	            })
	            .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
	    }
	    @PostMapping("/signup")
	    public Mono<ResponseEntity<String>> signup(@RequestBody Usuarios user) {
	    // Encrypt password before saving
	    user.setPassword(user.getPassword());
	    return userService.crearUsuario(user)
	            .map(savedUser -> ResponseEntity.ok("User signed up successfully"));
	    }

	    @GetMapping("/protected")
	    public Mono<ResponseEntity<String>> protectedEndpoint() {
	        return Mono.just(ResponseEntity.ok("You have accessed a protected endpoint!"));
	    }
}
