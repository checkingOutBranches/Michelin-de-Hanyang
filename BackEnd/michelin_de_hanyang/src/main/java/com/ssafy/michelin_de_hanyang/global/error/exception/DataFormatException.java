package com.ssafy.michelin_de_hanyang.global.error.exception;

import lombok.Getter;

import java.util.Arrays;

/**
 * 클라이언트의 요청이 잘못된 형식임.
 */
@Getter
public class DataFormatException extends RuntimeException {

	private final Object[] parameterValues;

	public DataFormatException(String message, Object... parameterValues) {
		super(message + " Parameter Value: " + Arrays.toString(parameterValues));
		this.parameterValues = parameterValues;
	}
}
