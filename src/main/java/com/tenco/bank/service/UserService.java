package com.tenco.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.interfaces.UserRepository;
import com.tenco.bank.repository.model.User;

@Service // Ioc 대상
public class UserService {

	@Autowired // DI 처리 (객체 생성시 의존 주의 처리)
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Transactional
	// 메서드 호출이 시작될 때 트랜잭션에 시작
	// 메서드 종료시 트랜잭션 종료(commit)
	public void createUser(SignUpFormDto signUpFormDto) {
		
		String hashPwd = passwordEncoder.encode(signUpFormDto.getPassword());
		signUpFormDto.setPassword(hashPwd);

		int result = userRepository.insert(signUpFormDto);
		if (result != 1) {
			throw new CustomRestfulException("회원가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/*
	 * 로그인 서비스 처리
	 * @param signInFormDto
	 * @return userEntity
	 * */
	@Transactional
	public User signIn(SignInFormDto signInFormDto) {
		
		
		User userEntity = userRepository.findByUsername(signInFormDto);
		
		if(userEntity == null) {
			throw new CustomRestfulException("아이디를 찾을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!passwordEncoder.matches(signInFormDto.getPassword(), userEntity.getPassword())) {
			throw new CustomRestfulException("패스워드가 틀렸습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return userEntity;
	}

}
