package com.my.erp.api.assembler.produto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.erp.api.model.produto.LojaProdutoModel;
import com.my.erp.api.model.produto.ProdutoModel;
import com.my.erp.api.model.produto.ProdutoResumoModel;
import com.my.erp.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoModel.class);
	}
	
	public ProdutoResumoModel toResumoModel(Produto produto) {
		return modelMapper.map(produto, ProdutoResumoModel.class);
	}
	
	public LojaProdutoModel toLojaModel(Produto produto) {
		return modelMapper.map(produto, LojaProdutoModel.class);
	}
	
	public List<ProdutoModel> toCollectionModel(Collection<Produto> produtos){
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
	}
	
	public List<ProdutoResumoModel> toCollectionResumoModel(Collection<Produto> produtos){
		return produtos.stream()
				.map(produto -> toResumoModel(produto))
				.collect(Collectors.toList());
	}
	
	public List<LojaProdutoModel> toCollectionLojaModel(Collection<Produto> produtos){
		return produtos.stream()
				.map(produto -> toLojaModel(produto))
				.collect(Collectors.toList());
	}

}
