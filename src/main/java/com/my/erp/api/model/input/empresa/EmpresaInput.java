package com.my.erp.api.model.input.empresa;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String razaoSocial;

}
