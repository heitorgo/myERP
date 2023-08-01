package com.my.erp.domain.exception;

public class FuncionarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FuncionarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public FuncionarioNaoEncontradoException(Long id) {
		super(String.format("Funcionario de código identificador %d não foi encontrado", id));
	}
	
	public FuncionarioNaoEncontradoException(Long funcionarioId, Long cargoId) {
		super(String.format("Funcionario de código identificador %d não foi encontrado para a cargo de código %d", funcionarioId, cargoId));
	}

}
