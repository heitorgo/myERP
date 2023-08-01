package com.my.erp.api.model.input.caixa;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.my.erp.api.model.input.loja.LojaIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaInput {
	
	@NotBlank
	@NotNull
	private String nome;
	
	@NotNull
	@Valid
	private LojaIdInput loja;

}
