package com.ssafy.michelin_de_hanyang.global.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponse {
	private int status;
	private String errorName;

	public ErrorResponse(int status, String errorName) {
		this.status = status;
		this.errorName = errorName;
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
		return ResponseEntity
			.status(e.getHttpStatus())
			.body(ErrorResponse.builder()
				.status(e.getHttpStatus().value())
				.errorName(e.getMessage())
				.build());
	}
}
