package com.my.erp.api.model.loja;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaResumoModel {
	
	private Long id;
	private String nome;
	private Boolean ativo;
	private Boolean aberto;

}
