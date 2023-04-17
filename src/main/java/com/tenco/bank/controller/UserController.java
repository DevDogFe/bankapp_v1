package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/sign-up")
	public String signUp() {
		
		return "user/signUp";
	}
	
	/*
	 * 회원가입처리
	 * @param signUpFormDto
	 * @return 로그인 페이지
	 * */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto signUpFormDto) {
		
		return "redirect:/user/sign-in";
	}
	/*
	 * 로그인 폼
	 * @return 로그인 페이지
	 * */
	@GetMapping("/sign-in")
	public String singIn() {
		
		return "user/signIn";
	}
	/*
	 * 로그인 처리
	 * @param signInFormDto
	 * @return 메인 페이지 이동(수정 예정)
	 * */
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto signInFormDto) {
		
		return "redirect:/test/main";
	}
	
	
	

}
