package com.my.erp.api.model.input.usuario;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIdInput {
	
	@NotNull
	private Long id;

}
