package com.my.erp.api.model.loja;

import com.my.erp.api.model.empresa.EmpresaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaModel {
	
	private Long id;
	private String nome;
	private EmpresaModel empresa;
	private Boolean ativo;
	private Boolean aberto;

}
