package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.Terms_Price_Config;

public interface Terms_Price_Config_Service {

	public ResponseEntity<String> saveTerms_Price_Config(Terms_Price_Config obj, String jwt) throws Exception;

	public ResponseEntity<String> editTerms_Price_Config(Terms_Price_Config obj, String jwt) throws Exception;
	
	public List<Terms_Price_Config> getTerms_Price_ConfigList() throws Exception;
	
	public List<String> getHeadNameByActivity(String activity) throws Exception;
	
	public List<String> getSupplyMode(String activity) throws Exception;

	public List<String> gePaymentMode(String activity) throws Exception;
	
	
}
