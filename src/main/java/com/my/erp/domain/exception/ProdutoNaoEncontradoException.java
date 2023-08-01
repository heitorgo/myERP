package com.my.erp.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ProdutoNaoEncontradoException(Long id) {
		super(String.format("Produto de código identificador %d não foi encontrado", id));
	}
	
	public ProdutoNaoEncontradoException(Long produtoId, Long lojaId) {
		super(String.format("Produto de código identificador %d não foi encontrado para a loja de código %d", produtoId, lojaId));
	}

}
