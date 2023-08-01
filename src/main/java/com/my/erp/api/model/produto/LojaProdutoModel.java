package com.my.erp.api.model.produto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojaProdutoModel {
	
	private Long id;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private BigDecimal valor;
	private Boolean ativo;

}
