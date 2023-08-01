package com.my.erp.api.model.caixa;

import com.my.erp.api.model.loja.LojaResumoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaResumoModel {
	
	private Long id;
	private String nome;
	private LojaResumoModel loja;
	private Boolean ativo;

}
