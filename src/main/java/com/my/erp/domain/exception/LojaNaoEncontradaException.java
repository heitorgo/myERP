package com.my.erp.domain.exception;

public class LojaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public LojaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public LojaNaoEncontradaException(Long id) {
		super(String.format("Loja de código identificador %d não foi encontrada", id));
	}

}
