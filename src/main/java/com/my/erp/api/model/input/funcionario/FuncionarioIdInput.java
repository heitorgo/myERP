package com.my.erp.api.model.input.funcionario;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioIdInput {
	
	@NotNull
	private Long id;

}
