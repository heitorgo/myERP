package com.my.erp.api.model.empresa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaModel {
	
	private Long id;
	private String nome;
	private String razaoSocial;
	private Boolean ativo;

}
