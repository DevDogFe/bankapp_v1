package com.tenco.bank.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizedException;

@RestControllerAdvice // IoC 대상 + AOP
public class MyRestfulExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void exception (Exception e) {
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
	}
	
	// 사용자 정의 예외 클래스 활용
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		// 반드시 마지막에 ; 붙여야 스크립트 동작
		sb.append("alert('" + e.getMessage() + "');");
		sb.append("history.back();");
		sb.append("</script>");
		return sb.toString();
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public String unAuthorizedException(UnAuthorizedException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		// 반드시 마지막에 ; 붙여야 스크립트 동작
		sb.append("alert('" + e.getMessage() + "');");
		sb.append("location.href='/user/sign-in'");
		sb.append("</script>");
		return sb.toString();
	}
	
}
