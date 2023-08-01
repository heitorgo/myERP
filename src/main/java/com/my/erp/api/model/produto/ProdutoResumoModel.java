package com.my.erp.api.model.produto;

import java.math.BigDecimal;

import com.my.erp.api.model.loja.LojaResumoModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoResumoModel {
	
	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private BigDecimal valor;
	private LojaResumoModel loja;
	private Boolean ativo;

}
