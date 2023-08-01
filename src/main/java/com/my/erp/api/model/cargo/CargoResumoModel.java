package com.my.erp.api.model.cargo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoResumoModel {
	
	private Long id;
	private String titulo;
	private Boolean ativo;
	private Long lojaId;
	private String lojaNome;

}
