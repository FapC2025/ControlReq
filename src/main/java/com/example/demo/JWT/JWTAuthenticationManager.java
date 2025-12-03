package com.example.demo.JWT;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.example.demo.service.UsuariosService;

import reactor.core.publisher.Mono;

@Component
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

	private final  JWTUtil jwtUtil;
	private final UsuariosService usuarioService;
	
	public JWTAuthenticationManager(JWTUtil jwtUtil , UsuariosService usuarioService ) {
		this.jwtUtil = jwtUtil;
		this.usuarioService = usuarioService;
	}

	@Override
	
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();

        try {
            String email = jwtUtil.extractUsername(token);

            return usuarioService.login(email)
                    .flatMap(usuario -> {
                        if (jwtUtil.validateToken(token, usuario.getEmail())) {
                            // ✅ crear Authentication válido
                            Authentication auth = new UsernamePasswordAuthenticationToken(
                                    usuario.getEmail(),
                                    null,
                                    null // aquí puedes agregar roles o authorities si los tienes
                            );
                            return Mono.just(auth);
                        } else {
                            return Mono.error(new AuthenticationException("Token inválido o expirado") {});
                        }
                    })
                    .onErrorResume(e -> Mono.error(new AuthenticationException("Error autenticando token", e) {}));

        } catch (Exception e) {
            return Mono.error(new AuthenticationException("Token inválido", e) {});
        }
    }
	public ServerAuthenticationConverter authenticationConverter() {
        return new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String token = exchange.getRequest().getHeaders().getFirst("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    return Mono.just(SecurityContextHolder.getContext().getAuthentication());
                }
                return Mono.empty();
            }
        };
    }
	
}
