package com.my.erp.api.assembler.loja;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.input.loja.LojaInput;
import com.my.erp.domain.model.Empresa;
import com.my.erp.domain.model.Loja;


@Component
public class LojaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Loja toDomainModel(LojaInput lojaInput) {
		return modelMapper.map(lojaInput, Loja.class);
	}
	
	public void copyToDomainObject(LojaInput lojaInput, Loja loja) {
		//para evitar erro de alteração de identificador de empresa
		loja.setEmpresa(new Empresa());
		
		modelMapper.map(lojaInput, loja);
	}

}
