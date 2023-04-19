package com.tenco.bank.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SaveFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.model.Account;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	/*
	 * 계좌 생성 기능
	 * @param saveFormDto
	 * @param PrincipalId
	 * */
	@Transactional
	public void createAccount(SaveFormDto saveFormDto, Integer principalId) {
		Account account = new Account();
		account.setNumber(saveFormDto.getNumber());
		account.setBalance(saveFormDto.getBalance());
		account.setPassword(saveFormDto.getPassword());
		account.setUserId(principalId);
		int resultRowCount = accountRepository.insert(account);
		
		if(resultRowCount != 1) {
			throw new CustomRestfulException("계좌 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	public ArrayList<Account> ReadAccountList(Integer userId){
		ArrayList<Account> list = accountRepository.findByUserId(userId);
		return list;
	}

}
