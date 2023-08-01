package com.my.erp.api.model.cargo;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaCargoModel {
	
	private Long id;
	private String titulo;
	private BigDecimal remuneracao;
	private Boolean ativo;

}
