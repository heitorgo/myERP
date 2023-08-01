package com.my.erp.api.model.funcionario;

import com.my.erp.api.model.cargo.CargoResumoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioResumoModel {
	
	private Long id;
	private String nome;
	private CargoResumoModel cargo;
	private Boolean ativo;

}
