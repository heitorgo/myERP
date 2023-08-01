package com.my.erp.domain.exception;

public class CaixaNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CaixaNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public CaixaNaoEncontradoException(Long id) {
		super(String.format("Caixa de código identificador %d não foi encontrado", id));
	}
	
	public CaixaNaoEncontradoException(Long caixaId, Long lojaId) {
		super(String.format("Caixa de código identificador %d não foi encontrado para loja de código %d", caixaId, lojaId));
	}

}
