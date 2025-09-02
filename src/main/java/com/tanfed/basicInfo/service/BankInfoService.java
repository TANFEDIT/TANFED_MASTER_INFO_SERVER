package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.BankInfo;
import com.tanfed.basicInfo.response.DataForOB;

public interface BankInfoService {

	public ResponseEntity<String> saveBankInfo(BankInfo obj, String jwt) throws Exception;
		
	public List<BankInfo> getBankInfoByOfficeName(String officeName) throws Exception;
	 
	public ResponseEntity<String> editBankInfo(BankInfo obj, String jwt) throws Exception;
	
	public DataForOB getDataForOBForm(String officeName, String bankName, String branchName, String accountType) throws Exception;
	
	public BankInfo getBankInfoByAccountNo(Long accountNumber) throws Exception;
}
