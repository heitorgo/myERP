package com.my.erp.domain.exception;

public class VendaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public VendaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public VendaNaoEncontradaException(Long id) {
		super(String.format("Venda de código identificador %d não foi encontrada", id));
	}
	
	public VendaNaoEncontradaException(Long vendaId, Long caixaId) {
		super(String.format("Venda de código identificador %d não foi encontrada para caixa de código %d", vendaId, caixaId));
	}

}