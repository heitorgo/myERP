package com.my.erp.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AcessoNegadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AcessoNegadoException(String mensagem) {
		super(mensagem);
	}

	public AcessoNegadoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
