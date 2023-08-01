package com.my.erp.api.model.input.cargo;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaCargoInput {
	
	@NotBlank
	private String titulo;
	
	@Positive
	private BigDecimal remuneracao;

}
