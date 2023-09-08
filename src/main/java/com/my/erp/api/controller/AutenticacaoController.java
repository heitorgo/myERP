package com.my.erp.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.erp.api.model.TokenJWTModel;
import com.my.erp.api.model.input.usuario.UsuarioAutenticacaoInput;
import com.my.erp.domain.exception.AuthenticationException;
import com.my.erp.domain.model.Usuario;
import com.my.erp.domain.service.TokenService;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	 @PostMapping
	 @ResponseStatus(HttpStatus.OK)
	 public TokenJWTModel efetuarLogin(@RequestBody @Valid UsuarioAutenticacaoInput usuarioInput) {
		 try {
			 var token = new UsernamePasswordAuthenticationToken(usuarioInput.getEmail(), usuarioInput.getSenha());
			 var authentication = manager.authenticate(token);
			 var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
			 
			 return new TokenJWTModel(tokenJWT);
		 }catch(BadCredentialsException | InternalAuthenticationServiceException e) {
			 throw new AuthenticationException("Usuário inexistente ou senha inválida", e);
		 }
	 }

}
