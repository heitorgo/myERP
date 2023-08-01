package com.my.erp.api.model.input.caixa;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaIdInput {
	
	@NotNull
	private Long id;

}
