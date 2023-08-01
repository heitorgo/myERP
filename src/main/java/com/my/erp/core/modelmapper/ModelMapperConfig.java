package com.my.erp.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.my.erp.api.model.input.itemVenda.ItemVendaInput;
import com.my.erp.domain.model.ItemVenda;


@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper =  new ModelMapper();
		
		modelMapper.createTypeMap(ItemVendaInput.class, ItemVenda.class)
			.addMappings(mapper -> mapper.skip(ItemVenda::setId));
		
		return modelMapper;
	}

}
