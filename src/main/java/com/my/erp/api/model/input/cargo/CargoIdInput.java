package com.my.erp.api.model.input.cargo;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoIdInput {
	
	@NotNull
	private Long id;

}
