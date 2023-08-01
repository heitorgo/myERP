package com.my.erp.api.model.input.funcionario;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoFuncionarioInput {
	
	@NotBlank
	private String nome;
}
