package com.my.erp.api.model.funcionario;

import com.my.erp.api.model.cargo.CargoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioModel {
	
	private Long id;
	private String nome;
	private CargoModel cargo;
	private Boolean ativo;

}
