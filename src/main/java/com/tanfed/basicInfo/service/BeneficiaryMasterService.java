package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.BeneficiaryMaster;

public interface BeneficiaryMasterService {

	public ResponseEntity<String> saveBeneficiaryMaster(List<BeneficiaryMaster> obj, String jwt) throws Exception;
	
	public List<BeneficiaryMaster> getBeneficiaryMasterByName(String beneficiaryName, String officeName) throws Exception;
	
	public List<BeneficiaryMaster> getBeneficiaryListByOfficeName() throws Exception;
	
	public ResponseEntity<String> editBeneficiaryMaster(BeneficiaryMaster obj, String jwt) throws Exception;
	
//	public List<String> getBeneficiaryNameListByOfficeName(String officeName) throws Exception;

	public List<String> fetchDataForBeneficiaryMaster(String officeName) throws Exception;
}
