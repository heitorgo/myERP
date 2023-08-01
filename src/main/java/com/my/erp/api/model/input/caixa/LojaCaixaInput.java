package com.my.erp.api.model.input.caixa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaCaixaInput {
	
	@NotBlank
	@NotNull
	private String nome;

}
