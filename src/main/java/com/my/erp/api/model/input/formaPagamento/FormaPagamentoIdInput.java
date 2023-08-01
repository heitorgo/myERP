package com.my.erp.api.model.input.formaPagamento;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInput {
	
	@NotNull
	private Long id;

}
