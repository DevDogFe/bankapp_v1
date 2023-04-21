package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bank.dto.response.HistoryDto;
import com.tenco.bank.repository.model.History;

@Mapper
public interface HistoryRepository {

	public int insert(History history);
	
	public int updateById(History history);
	
	public int deleteByID(Integer id);
	
	public History findById(Integer id);
	
	public List<History> findAll();
	// 매개변수 갯수가 2개 이상이면 반드시 파라미터 이름을 명시해줘야한다.
	public List<HistoryDto> findByIdHistoryType(@Param("type") String type, @Param("id") Integer id);
}
