package com.my.erp.domain.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.my.erp.domain.model.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String gerarToken(Usuario usuario) {
		try {
			var algoritimo = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("API MyERP")
					.withSubject(usuario.getEmail())
					.withExpiresAt(dataExpiracao())
					.sign(algoritimo);
		}catch(JWTCreationException e) {
			throw new RuntimeException("erro ao gerar token JWT", e);
		}		
	}
	
	public String getSubject(String tokenJWT) {
		try {
			var algoritmo = Algorithm.HMAC256(secret);
			return JWT.require(algoritmo)
					.withIssuer("API MyERP")
					.build()
					.verify(tokenJWT)
					.getSubject();
		}catch(JWTVerificationException e) {
			throw new AccessDeniedException("Token JWT inválido ou expirado!");
		}
	}
	
	private Instant dataExpiracao() {
		return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
	}

}
