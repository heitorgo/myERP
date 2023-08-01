package com.my.erp.domain.exception;

public class EmpresaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EmpresaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public EmpresaNaoEncontradaException(Long id) {
		super(String.format("Empresa de código identificador %d não foi encontrada", id));
	}

}
