package com.ssafy.michelin_de_hanyang.global.error.exception;

public class TokenValidationException extends RuntimeException {

	public TokenValidationException(String message) {
		super(message);
	}
}
