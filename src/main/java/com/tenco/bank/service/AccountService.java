package com.tenco.bank.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SaveFormDto;
import com.tenco.bank.dto.WithdrawFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.interfaces.AccountRepository;
import com.tenco.bank.repository.interfaces.HistoryRepository;
import com.tenco.bank.repository.model.Account;
import com.tenco.bank.repository.model.History;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private HistoryRepository historyRepository;
	
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
	/*
	 * 소유한 계좌 목록 보기
	 * */
	@Transactional
	public ArrayList<Account> ReadAccountList(Integer userId){
		ArrayList<Account> list = accountRepository.findByUserId(userId);
		return list;
	}
	
	/*
	 * 출금기능 구현
	 * 1. 계좌 존재 여부, 비밀번호, 잔액 확인
	 * */
	@SuppressWarnings("unused")
	@Transactional
	public int updateAccountWithdraw(WithdrawFormDto withdrawFormDto, Integer principalId) {
		
		Account accountEntity = accountRepository.findByNumber(withdrawFormDto.getWAccountNumber());
		if(accountEntity == null) {
			throw new CustomRestfulException("계좌가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		if(accountEntity.getUserId() != principalId) {
			throw new CustomRestfulException("본인 소유 계좌가 아닙니다.", HttpStatus.UNAUTHORIZED);
		}
		if(!accountEntity.getPassword().equals(withdrawFormDto.getWAccountPassword())) {
			throw new CustomRestfulException("출금계좌의 비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED);
		}
		if(accountEntity.getBalance() < withdrawFormDto.getAmount()) {
			throw new CustomRestfulException("계좌의 잔액이 부족합니다.", HttpStatus.BAD_REQUEST);
		}
		accountEntity.widthdraw(withdrawFormDto.getAmount());
		accountRepository.updateById(accountEntity);
		
		History history = new History();
		history.setAmount(withdrawFormDto.getAmount());
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null);
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		int resultRowCount = historyRepository.insert(history);
		
		if(resultRowCount != 1) {
			throw new CustomRestfulException("출금이 정상처리되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return 0;
	}

}
