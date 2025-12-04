package com.example.demo.security;

import java.net.http.HttpHeaders;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AnonymousAuthenticationWebFilter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.demo.JWT.JWTAuthenticationManager;

import reactor.core.publisher.Mono;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private final JWTAuthenticationManager authenticationManager;

	public SecurityConfig(JWTAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

//	@Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf(csrf -> csrf.disable()
//                        .authorizeExchange(exchange -> exchange
//                                .pathMatchers(
//                                        ""
//                                , "/signup"
//                                , "/swagger-ui.html",
//                                        "/swagger-ui/**",
//                                        "/v3/api-docs/**",
//                                        "/v3/api-docs/swagger-config", // 👈 importante en springdoc-openapi
//                                        "/webjars/**",
//                                        "/favicon.ico",
//                                        "/api/login"// Otros endpoints públicos
//                                ).permitAll()
//                                .anyExchange().authenticated()))
//                .authenticationManager(authenticationManager)
//                .build();
//                
//    }
	@Bean
	    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
	        ServerAuthenticationConverter bearer = ex -> Mono.justOrEmpty(ex.getRequest().getHeaders().getFirst(org.springframework.http.HttpHeaders.AUTHORIZATION))
	        		                                         .filter(h-> h.startsWith("Bearer "))
	        		                                         .map(h-> h.substring(7))
	        		                                         .map(t -> new UsernamePasswordAuthenticationToken("N/A", t));
			AuthenticationWebFilter filter = new AuthenticationWebFilter(authenticationManager);
			filter.setServerAuthenticationConverter(bearer);
			filter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
	        return http
	                .csrf(ServerHttpSecurity.CsrfSpec::disable)
	                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
	                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita y configura CORS

	                .authorizeExchange(exchange -> exchange
                          .pathMatchers(
                 "/signup"
                  , "/swagger-ui.html",
                          "/swagger-ui/**",
                          "/v3/api-docs/**",
                          "/v3/api-docs/swagger-config", // 👈 importante en springdoc-openapi
                          "/webjars/**",
                          "/favicon.ico",
                          "/api/crearUsuario",
                          "/api/login"// Otros endpoints públicos
                  ).permitAll()
                  .anyExchange().authenticated()).addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION).build();
	                
	                
	    }
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://tu-frontend.com")); // Define tus orígenes permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permite cabeceras
        configuration.setAllowCredentials(true); // Permite credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
}
