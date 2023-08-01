package com.my.erp.api.model.venda;

import java.math.BigDecimal;
import java.util.List;

import com.my.erp.api.model.formaPagamento.FormaPagamentoModel;
import com.my.erp.api.model.funcionario.CargoFuncionarioModel;
import com.my.erp.api.model.itemVenda.ItemVendaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaVendaModel {
	
	private Long id;
	private BigDecimal valor;
	private String descricao;
	private CargoFuncionarioModel funcionario;
	private FormaPagamentoModel formaPagamento;
	private List<ItemVendaModel> itens;
}
