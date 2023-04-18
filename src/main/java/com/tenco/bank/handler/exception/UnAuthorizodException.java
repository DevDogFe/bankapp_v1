package com.tenco.bank.handler.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnAuthorizodException extends RuntimeException{

	private HttpStatus status;
	
	public UnAuthorizodException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
}
