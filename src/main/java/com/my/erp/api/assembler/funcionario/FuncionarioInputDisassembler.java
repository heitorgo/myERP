package com.my.erp.api.assembler.funcionario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.input.funcionario.CargoFuncionarioInput;
import com.my.erp.api.model.input.funcionario.FuncionarioInput;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Funcionario;


@Component
public class FuncionarioInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Funcionario toDomainModel(FuncionarioInput funcionarioInput) {
		return modelMapper.map(funcionarioInput, Funcionario.class);
	}
	
	public Funcionario toDomainModel(CargoFuncionarioInput cargoFuncionarioInput) {
		return modelMapper.map(cargoFuncionarioInput, Funcionario.class);
	}
	
	public void copyToDomainObject(FuncionarioInput funcionarioInput, Funcionario funcionario) {
		//para evitar erro de alteração de identificador de cargo
		funcionario.setCargo(new Cargo());
		
		modelMapper.map(funcionarioInput, funcionario);
	}
	
	public void copyToDomainObject(CargoFuncionarioInput cargoFuncionarioInput, Funcionario funcionario) {
		modelMapper.map(cargoFuncionarioInput, funcionario);
	}

}
