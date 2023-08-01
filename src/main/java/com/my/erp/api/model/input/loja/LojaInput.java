package com.my.erp.api.model.input.loja;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.my.erp.api.model.input.empresa.EmpresaIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaInput {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Valid
	private EmpresaIdInput empresa;

}
