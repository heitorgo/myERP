package com.my.erp.api.assembler.caixa;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.input.caixa.CaixaInput;
import com.my.erp.api.model.input.caixa.LojaCaixaInput;
import com.my.erp.domain.model.Caixa;
import com.my.erp.domain.model.Loja;

@Component
public class CaixaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Caixa toDomainModel(CaixaInput caixaInput) {
		return modelMapper.map(caixaInput, Caixa.class);
	}
	
	public Caixa toDomainModel(LojaCaixaInput lojaCaixaInput) {
		return modelMapper.map(lojaCaixaInput, Caixa.class);
	}
	
	public void copyToDomainObject(CaixaInput caixaInput, Caixa caixa) {
		//para evitar erro de alteração de identificador de loja
		caixa.setLoja(new Loja());
		
		modelMapper.map(caixaInput, caixa);
	}
	
	public void copyToDomainObject(LojaCaixaInput lojaCaixaInput, Caixa caixa) {
		modelMapper.map(lojaCaixaInput, caixa);
	}

}
