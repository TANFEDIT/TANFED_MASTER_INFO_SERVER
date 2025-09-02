package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.AccountsMaster;

public interface AccountsMasterService {

	public ResponseEntity<String> saveAccountsMaster(List<AccountsMaster> obj, String jwt) throws Exception;
	
	public List<AccountsMaster> accountsMasterList() throws Exception;
	
	public ResponseEntity<String> editAccountsMaster(AccountsMaster obj, String jwt) throws Exception;
	
	public List<String> getMainHead() throws Exception;
	
	public List<String> getSubHeadByMainHead(String mainHead) throws Exception;
	
	
}
