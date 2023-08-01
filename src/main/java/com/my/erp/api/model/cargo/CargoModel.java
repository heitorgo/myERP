package com.my.erp.api.model.cargo;

import java.math.BigDecimal;

import com.my.erp.api.model.loja.LojaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoModel {
	
	private Long id;
	private String titulo;
	private BigDecimal remuneracao;
	private LojaModel loja;
	private Boolean ativo;

}
