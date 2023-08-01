package com.my.erp.api.model.itemVenda;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaModel {
	
	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal valorTotal;
	private BigDecimal valorUnitario;

}
