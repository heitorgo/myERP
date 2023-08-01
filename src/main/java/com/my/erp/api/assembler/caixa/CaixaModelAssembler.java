package com.my.erp.api.assembler.caixa;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.caixa.CaixaModel;
import com.my.erp.api.model.caixa.CaixaResumoModel;
import com.my.erp.api.model.caixa.LojaCaixaModel;
import com.my.erp.domain.model.Caixa;


@Component
public class CaixaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CaixaModel toModel(Caixa caixa) {
		return modelMapper.map(caixa, CaixaModel.class);
	}
	
	public CaixaResumoModel toResumoModel(Caixa caixa) {
		return modelMapper.map(caixa, CaixaResumoModel.class);
	}
	
	public LojaCaixaModel toLojaModel(Caixa caixa) {
		return modelMapper.map(caixa, LojaCaixaModel.class);
	}
	
	public List<CaixaModel> toCollectionModel(List<Caixa> caixas){
		return caixas.stream()
				.map(caixa -> toModel(caixa))
				.collect(Collectors.toList());
	}
	
	public List<CaixaResumoModel> toCollectionResumoModel(List<Caixa> caixas){
		return caixas.stream()
				.map(caixa -> toResumoModel(caixa))
				.collect(Collectors.toList());
	}
	
	public List<LojaCaixaModel> toCollectionLojaModel(List<Caixa> caixas){
		return caixas.stream()
				.map(caixa -> toLojaModel(caixa))
				.collect(Collectors.toList());
	}

}
