package com.tenco.bank.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.DepositFormDto;
import com.tenco.bank.dto.SaveFormDto;
import com.tenco.bank.dto.TransferFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.handler.exception.UnAuthorizodException;
import com.tenco.bank.repository.model.Account;
import com.tenco.bank.repository.model.User;
import com.tenco.bank.service.AccountService;
import com.tenco.bank.utils.Define;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private HttpSession session;
	@Autowired
	private AccountService accountService;

	/*
	 * 계좌 목록 페이지
	 * 
	 * @return 목록 페이지 이동
	 */
	@GetMapping({ "/list", "/" })
	public String list(Model model) {
		
		// 인증 검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}
		// 1. 
		List<Account> accountList = accountService.ReadAccountList(principal.getId());
		// Model, ModelAndView: view 화면으로 데이터를 내려주는 기술
		if(accountList.isEmpty()) {
			model.addAttribute("accountList", null);
		} else {
			model.addAttribute("accountList", accountList);
		}
		

		return "account/list";
	}

	// 출금 페이지
	@GetMapping("/withdraw")
	public String withdraw() {
		// 인증 검사
		if(session.getAttribute(Define.PRINCIPAL) == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}

		return "account/withdrawForm";
	}

	// 출금 페이지
	@PostMapping("/withdraw-proc")
	public String withdrawProc(WithdrawFormDto withdrawFormDto) {
		// 인증 검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}
		if(withdrawFormDto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(withdrawFormDto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("출금액이 0원 이하일수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if(withdrawFormDto.getWAccountNumber() == null || withdrawFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(withdrawFormDto.getWAccountPassword() == null || withdrawFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountWithdraw(withdrawFormDto, principal.getId());
		
		return "redirect:/account/list";
	}
	
	// 입금 페이지
	@GetMapping("/deposit")
	public String deposit() {
		// 인증 검사
		if(session.getAttribute(Define.PRINCIPAL) == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}

		return "account/depositForm";
	}
	
	// 입금 페이지
	@PostMapping("/deposit-proc")
	public String depositProc(DepositFormDto depositFormDto) {
		// 인증 검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}
		if(depositFormDto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(depositFormDto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("입금 금액은 0원 이하일 수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if(depositFormDto.getDAccountNumber() == null || depositFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력하세요/.", HttpStatus.BAD_REQUEST);
		}
		
		accountService.updateAccountDeposit(depositFormDto);
		
		return "redirect:/account/list";
	}

	// 이체 페이지
	@GetMapping("/transfer")
	public String transfer() {
		// 인증 검사
		if(session.getAttribute(Define.PRINCIPAL) == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}
		

		return "account/transferForm";
	}
	// 이체 post 페이지
	@PostMapping("/transfer-proc")
	public String transferProc(TransferFormDto transferFormDto) {
		// 인증 검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}
		// 유효성 검사
		if(transferFormDto.getAmount() == null) {
			throw new CustomRestfulException("금액을 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getAmount().longValue() <= 0) {
			throw new CustomRestfulException("이체금액이 0원 이하일수 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getWAccountNumber() == null || transferFormDto.getWAccountNumber().isEmpty()) {
			throw new CustomRestfulException("출금할 계좌번호를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getDAccountNumber() == null || transferFormDto.getDAccountNumber().isEmpty()) {
			throw new CustomRestfulException("이체할 계좌번호를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getWAccountPassword() == null || transferFormDto.getWAccountPassword().isEmpty()) {
			throw new CustomRestfulException("출금 계좌의 비밀번호를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if(transferFormDto.getWAccountNumber().equals(transferFormDto.getDAccountNumber())) {
			throw new CustomRestfulException("출금계좌와 이체계좌가 같습니다.", HttpStatus.BAD_REQUEST);
		}
		// 서비스 호출
		accountService.updateAccountTransfer(transferFormDto, principal.getId());
		
		return "redirect:/account/list";
	}

	// 계좌 개설 페이지
	@GetMapping("/save")
	public String save() {
		// 인증 검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}

		return "account/saveForm";
	}
	
	
	/*
	 * 계좌 생성
	 * 인증 검사
	 * 유효성 검사 처리
	 * @param saveFormDto
	 * @return 계좌 목록 페이지
	 * */
	@PostMapping("/save-proc")
	public String saveProc(SaveFormDto saveFormDto) {
		// 인증검사
		User principal = (User)session.getAttribute(Define.PRINCIPAL);
		if(principal == null) {
			throw new UnAuthorizodException("서비스를 이용하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
		}
		// 유효성 검사
		if(saveFormDto.getNumber() == null || saveFormDto.getNumber().isEmpty()) {
			throw new CustomRestfulException("계좌번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(saveFormDto.getPassword() == null || saveFormDto.getPassword().isEmpty()) {
			throw new CustomRestfulException("비밀번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}
		if(saveFormDto.getBalance() == null || saveFormDto.getBalance() < 0) {
			throw new CustomRestfulException("잘못된 금액입니다.", HttpStatus.BAD_REQUEST);
		}
		
		accountService.createAccount(saveFormDto, principal.getId());
		
		
		return "redirect:/account/list";
		
	}

	// 계좌 상세보기 페이지
	@GetMapping("/detail")
	public String detail() {
		return "account/detail";
	}

}
