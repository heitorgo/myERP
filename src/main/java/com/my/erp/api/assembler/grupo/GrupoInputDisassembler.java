package com.my.erp.api.assembler.grupo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.input.grupo.GrupoInput;
import com.my.erp.domain.model.Grupo;


@Component
public class GrupoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainModel(GrupoInput grupoInput) {
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		modelMapper.map(grupoInput, grupo);
	}

}
