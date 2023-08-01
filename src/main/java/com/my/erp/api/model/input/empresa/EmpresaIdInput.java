package com.my.erp.api.model.input.empresa;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaIdInput {
	
	@NotNull
	private Long id;

}
