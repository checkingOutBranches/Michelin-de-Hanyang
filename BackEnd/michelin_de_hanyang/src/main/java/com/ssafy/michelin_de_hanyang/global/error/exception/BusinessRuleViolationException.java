package com.ssafy.michelin_de_hanyang.global.error.exception;

public class BusinessRuleViolationException extends RuntimeException {
	public BusinessRuleViolationException(String message) {
		super(message);
	}
}
