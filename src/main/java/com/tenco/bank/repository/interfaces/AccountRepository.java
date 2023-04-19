package com.tenco.bank.repository.interfaces;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.repository.model.Account;

@Mapper
public interface AccountRepository {

	public int insert(Account account);

	public int updateById(Account account);

	public int deleteById(Integer id);

	public ArrayList<Account> findByAll();

	public Account findById(Integer id);

	public ArrayList<Account> findByUserId(Integer userId);
	
	public Account findByNumber(String number);
}
