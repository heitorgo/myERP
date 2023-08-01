package com.my.erp.api.assembler.funcionario;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.funcionario.CargoFuncionarioModel;
import com.my.erp.api.model.funcionario.FuncionarioModel;
import com.my.erp.api.model.funcionario.FuncionarioResumoModel;
import com.my.erp.domain.model.Funcionario;

@Component
public class FuncionarioModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FuncionarioModel toModel(Funcionario funcionario) {
		return modelMapper.map(funcionario, FuncionarioModel.class);
	}
	
	public FuncionarioResumoModel toResumoModel(Funcionario funcionario) {
		return modelMapper.map(funcionario, FuncionarioResumoModel.class);
	}
	
	public CargoFuncionarioModel toCargoModel(Funcionario funcionario) {
		return modelMapper.map(funcionario, CargoFuncionarioModel.class);
	}
	
	public List<FuncionarioModel> toCollectionModel(List<Funcionario> funcionarios){
		return funcionarios.stream()
				.map(funcionario -> toModel(funcionario))
				.collect(Collectors.toList());
	}
	
	public List<FuncionarioResumoModel> toCollectionResumoModel(List<Funcionario> funcionarios){
		return funcionarios.stream()
				.map(funcionario -> toResumoModel(funcionario))
				.collect(Collectors.toList());
	}
	
	public List<CargoFuncionarioModel> toCollectionCargoModel(List<Funcionario> funcionarios){
		return funcionarios.stream()
				.map(funcionario -> toCargoModel(funcionario))
				.collect(Collectors.toList());
	}

}