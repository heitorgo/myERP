package com.my.erp.api.model.input.itemVenda;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaInput {
	
	@NotNull
	private Long produtoId;
	
	@NotNull
	@Positive
	private int quantidade;

}
