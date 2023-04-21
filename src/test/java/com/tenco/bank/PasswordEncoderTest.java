package com.tenco.bank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	
	public static void main(String[] args) {
		// 기능 확인해보기
		
		String password = "p1234";
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println("암호화 비밀번호: " + hashedPassword);
		boolean isMatched = passwordEncoder.matches(password, hashedPassword);
		System.out.println(isMatched);
		
	}

}
