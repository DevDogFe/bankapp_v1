package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

// IoC 대상 아님, 필요할 때 직접 처리
@Getter
public class CustomRestfulException extends RuntimeException {
	
	private HttpStatus status;
	
	// throw new CustomRestfulException('페이지 못찾음', 404);
	public CustomRestfulException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

}
