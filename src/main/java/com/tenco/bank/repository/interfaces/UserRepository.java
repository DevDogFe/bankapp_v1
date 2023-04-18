package com.tenco.bank.repository.interfaces;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.repository.model.User;

@Mapper // MyBatis 의존 설정 선행 (build.gradle 파일)
public interface UserRepository {

	public int insert(SignUpFormDto signUpFormDto);

	public int updateById(User user);

	public int deleteById(Integer id);

	public User findById(Integer id);

	public ArrayList<User> findAll();

}
