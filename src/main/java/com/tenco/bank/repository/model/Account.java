package com.tenco.bank.repository.model;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.tenco.bank.handler.exception.CustomRestfulException;

import lombok.Data;
import lombok.Setter;

/*
 * 모델 클래스(Value Object 역할만 하는 것은 아니다.)
 * */
@Data
public class Account {

	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAt;

	public void widthdraw(Long amount) {
		this.balance -= amount;
	}

	public void deposit(Long amount) {
		this.balance += amount;
	}
	
	// 패스워드 체크
	public void checkPassword(String password) {
		if(!this.password.equals(password)) {
			throw new CustomRestfulException("계좌 비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
		}
	}
	// 잔액 여부 확인(출금시)
	public void checkBalance(Long amount) {
		if(balance < amount) {
			throw new CustomRestfulException("잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
		}
	}
	// 계좌 소유주 확인
	public void checkOwner(Integer principalId) {
		if(userId != principalId) {
			throw new CustomRestfulException("본인계좌에서만 출금할 수 있습니다.", HttpStatus.FORBIDDEN);
		}
	}
	
}
