package com.tenco.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;

	/*
	 * 회원가입 폼
	 * @return 회원가입 페이지
	 */
	@GetMapping("/sign-up")
	public String signUp() {

		return "user/signUp";
	}

	/*
	 * 회원가입처리
	 * @param signUpFormDto
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
		userService.createUser(signUpFormDto);

		return "redirect:/user/sign-in";
	}

	/*
	 * 로그인 폼
	 * @return 로그인 페이지
	 */
	@GetMapping("/sign-in")
	public String singIn() {

		return "user/signIn";
	}

	/*
	 * 로그인 처리
	 * @param signInFormDto
	 * @return 메인 페이지 이동(수정 예정)
	 * Get 방식 처리는 브라우저 히스토리에 남겨지기 때문에
	 * 예외적으로 로그인은 POST 방식으로 처리한다.
	 */
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto signInFormDto) {
		
		// 1. 유효성 검사
		if(signInFormDto.getUsername() == null || signInFormDto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(signInFormDto.getPassword() == null || signInFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		
		User principal = userService.signIn(signInFormDto);
		principal.setPassword(null);
		
		session.setAttribute(Define.PRINCIPAL, principal);
		
		
		return "redirect:/account/list";
	}
	
	/*
	 * 로그아웃 프로세스
	 * @return 로그인 페이지
	 */
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		
		return "redirect:/user/sign-in";
	}

}
