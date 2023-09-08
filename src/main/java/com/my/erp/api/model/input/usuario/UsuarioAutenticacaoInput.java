package com.my.erp.api.model.input.usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAutenticacaoInput{
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String senha;
	
}
