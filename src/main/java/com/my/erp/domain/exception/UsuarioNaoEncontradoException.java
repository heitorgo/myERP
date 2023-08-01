package com.my.erp.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long id) {
		super(String.format("Usuario de código identificador %d não foi encontrado", id));
	}

}
