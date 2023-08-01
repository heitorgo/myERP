package com.my.erp.api.model.input.produto;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoIdInput {
	
	@NotNull
	private Long id;

}
