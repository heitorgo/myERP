package com.my.erp.api.assembler.cargo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.input.cargo.CargoInput;
import com.my.erp.api.model.input.cargo.LojaCargoInput;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Loja;


@Component
public class CargoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cargo toDomainModel(CargoInput cargoInput) {
		return modelMapper.map(cargoInput, Cargo.class);
	}
	
	public Cargo toDomainModel(LojaCargoInput cargoLojaInput) {
		return modelMapper.map(cargoLojaInput, Cargo.class);
	}
	
	public void copyToDomainObject(CargoInput cargoInput, Cargo cargo) {
		//para evitar erro de alteração de identificador de loja
		cargo.setLoja(new Loja());
		
		modelMapper.map(cargoInput, cargo);
	}
	
	public void copyToDomainObject(LojaCargoInput cargoLojaInput, Cargo cargo) {
		modelMapper.map(cargoLojaInput, cargo);
	}

}
