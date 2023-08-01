package com.my.erp.api.model.caixa;

import com.my.erp.api.model.loja.LojaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaModel {
	
	private Long id;
	private String nome;
	private LojaModel loja;
	private Boolean ativo;

}
