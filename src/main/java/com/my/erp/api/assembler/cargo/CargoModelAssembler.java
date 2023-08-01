package com.my.erp.api.assembler.cargo;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.cargo.CargoModel;
import com.my.erp.api.model.cargo.CargoResumoModel;
import com.my.erp.api.model.cargo.LojaCargoModel;
import com.my.erp.domain.model.Cargo;

@Component
public class CargoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CargoModel toModel(Cargo cargo) {
		return modelMapper.map(cargo, CargoModel.class);
	}
	
	public CargoResumoModel toResumoModel(Cargo cargo) {
		return modelMapper.map(cargo, CargoResumoModel.class);
	}
	
	public LojaCargoModel toLojaModel(Cargo cargo) {
		return modelMapper.map(cargo, LojaCargoModel.class);
	}
	
	
	public List<CargoModel> toCollectionModel(List<Cargo> cargos){
		return cargos.stream()
				.map(cargo -> toModel(cargo))
				.collect(Collectors.toList());
	}
	
	public List<CargoResumoModel> toCollectionResumoModel(List<Cargo> cargos){
		return cargos.stream()
				.map(cargo -> toResumoModel(cargo))
				.collect(Collectors.toList());
	}
	
	public List<LojaCargoModel> toCollectionLojaModel(List<Cargo> cargos){
		return cargos.stream()
				.map(cargo -> toLojaModel(cargo))
				.collect(Collectors.toList());
	}

}
