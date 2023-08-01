package com.my.erp.api.model.input.grupo;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {
	
	@NotBlank
	private String nome;

}
