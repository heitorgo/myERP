package com.my.erp.api.model.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	
	private Long id;
	private String nome;
	private String email;
	private Boolean ativo;

}
