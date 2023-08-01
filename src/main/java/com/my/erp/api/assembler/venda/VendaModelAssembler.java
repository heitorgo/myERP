package com.my.erp.api.assembler.venda;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.venda.CaixaVendaModel;
import com.my.erp.api.model.venda.VendaModel;
import com.my.erp.api.model.venda.VendaResumoModel;
import com.my.erp.domain.model.Venda;

@Component
public class VendaModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public VendaModel toModel(Venda venda) {
		return modelMapper.map(venda, VendaModel.class);
	}
	
	public VendaResumoModel toResumoModel(Venda venda) {
		return modelMapper.map(venda, VendaResumoModel.class);
	}
	
	public CaixaVendaModel toCaixaModel(Venda venda) {
		return modelMapper.map(venda, CaixaVendaModel.class);
	}
	
	public List<VendaModel> toCollectionModel(List<Venda> vendas){
		return vendas.stream()
				.map(venda -> toModel(venda))
				.collect(Collectors.toList());
	}
	
	public List<VendaResumoModel> toCollectionResumoModel(List<Venda> vendas){
		return vendas.stream()
				.map(venda -> toResumoModel(venda))
				.collect(Collectors.toList());
	}
	
	public List<CaixaVendaModel> toCollectionCaixaModel(List<Venda> vendas){
		return vendas.stream()
				.map(venda -> toCaixaModel(venda))
				.collect(Collectors.toList());
	}
}
