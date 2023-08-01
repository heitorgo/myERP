package com.my.erp.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FormaPagamentoNaoEncontradaException(Long id) {
		super(String.format("Forma pagamento de código identificador %d não foi encontrada", id));
	}

}
