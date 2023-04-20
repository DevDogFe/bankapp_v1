package com.tenco.bank.dto;

import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class TransferFormDto {
	
	private Long amount;
	private String wAccountNumber;
	private String wAccountPassword;
	private String dAccountNumber;
	
}
