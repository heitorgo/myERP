package com.my.erp.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem Incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ENTIDADE_INATIVA("/entidade-inativa", "Entidade inativa"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ERRO_TIPO_NAO_SUPORTADO("/tipo-nao-suportado", "Erro tipo não suportado"),
	ERRO_SISTEMA("/erro-sistema", "Erro de sistema"), DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro inválido");

	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://mercado-api.com.br" + path;
		this.title = title;
	}

}
