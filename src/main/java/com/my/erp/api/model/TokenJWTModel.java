package com.my.erp.api.model;

import lombok.Getter;

@Getter
public class TokenJWTModel {
	
	public TokenJWTModel(String token) {
		this.token = token;
	}

	private String token;

}
