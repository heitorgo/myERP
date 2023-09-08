package com.my.erp.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.my.erp.domain.repository.UsuarioRepository;
import com.my.erp.domain.service.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var tokenJWT = recuperarToken(request);
		
		if(tokenJWT != null) {
			var subject = tokenService.getSubject(tokenJWT);
			var usuario = usuarioRepository.findByEmail_User(subject);
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private String recuperarToken(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}

}
