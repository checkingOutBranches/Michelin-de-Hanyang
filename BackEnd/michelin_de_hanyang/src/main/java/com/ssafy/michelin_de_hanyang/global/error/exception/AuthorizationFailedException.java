package com.ssafy.michelin_de_hanyang.global.error.exception;

public class AuthorizationFailedException extends RuntimeException {

	/**
	 * 서버가 요청을 이해했으나, 권한 부족으로 거절함.
	 */
	public AuthorizationFailedException(String message) {
		super(message);
	}
}
