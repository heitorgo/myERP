package com.my.erp.api.model.input.venda;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.my.erp.api.model.input.formaPagamento.FormaPagamentoIdInput;
import com.my.erp.api.model.input.funcionario.FuncionarioIdInput;
import com.my.erp.api.model.input.itemVenda.ItemVendaInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaixaVendaInput {
	
	@NotBlank
	private String descricao;

	@Valid
	private FuncionarioIdInput funcionario;
	
	@Valid
	@NotNull
	private FormaPagamentoIdInput formaPagamento;
	
	@Size(min = 1)
	@Valid
	@NotNull
	private List<ItemVendaInput> itens;
	
}
