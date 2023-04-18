package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/*
	 * 회원가입 폼
	 * 
	 * @return 회원가입 페이지
	 */
	@GetMapping("/sign-up")
	public String signUp() {

		return "user/signUp";
	}

	/*
	 * 회원가입처리
	 * 
	 * @param signUpFormDto
	 * 
	 * @return 로그인 페이지
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {

		// 1. 유효성 검사(옛날방식): 프런트와 백에서 둘다 해야 바람직
		if (signUpFormDto.getUsername() == null || signUpFormDto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if (signUpFormDto.getPassword() == null || signUpFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if (signUpFormDto.getFullname() == null || signUpFormDto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		// 서비스 호출
		userService.signUp(signUpFormDto);

		return "redirect:/user/sign-in";
	}

	/*
	 * 로그인 폼
	 * 
	 * @return 로그인 페이지
	 */
	@GetMapping("/sign-in")
	public String singIn() {

		return "user/signIn";
	}

	/*
	 * 로그인 처리
	 * 
	 * @param signInFormDto
	 * 
	 * @return 메인 페이지 이동(수정 예정)
	 */
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto signInFormDto) {

		return "redirect:/test/main";
	}

}
