package com.my.erp.api.model.input.funcionario;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.my.erp.api.model.input.cargo.CargoIdInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioInput {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@Valid
	private CargoIdInput cargo;

}
