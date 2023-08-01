package com.my.erp.api.assembler.empresa;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.input.empresa.EmpresaInput;
import com.my.erp.domain.model.Empresa;


@Component
public class EmpresaInputDisassember {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Empresa toDomainModel(EmpresaInput empresaInput) {
		return modelMapper.map(empresaInput, Empresa.class);
	}
	
	public void copyToDomainObject(EmpresaInput empresaInput, Empresa empresa) {
		modelMapper.map(empresaInput, empresa);
	}

}
