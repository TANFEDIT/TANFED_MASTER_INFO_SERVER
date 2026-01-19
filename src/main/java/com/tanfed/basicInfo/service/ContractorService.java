package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.ContractorChargesData;
import com.tanfed.basicInfo.entity.ContractorInfo;
import com.tanfed.basicInfo.entity.DistanceMapping;
import com.tanfed.basicInfo.model.ContractorDto;
import com.tanfed.basicInfo.model.ContractorEmdEntryDto;
import com.tanfed.basicInfo.model.ContractorStatusUpdateDto;
import com.tanfed.basicInfo.model.DistanceMappingAbstractTable;
import com.tanfed.basicInfo.response.DataForContractorStatus;
import com.tanfed.basicInfo.response.DataForDistanceMapping;
import com.tanfed.basicInfo.response.DataForEmdEntry;
import com.tanfed.basicInfo.response.DataForEmdRefund;
import com.tanfed.basicInfo.response.DataForRateUpdate;

public interface ContractorService {

	public ResponseEntity<String> saveContractorInfo(ContractorDto obj, String jwt) throws Exception;
	
	public ContractorInfo getContractorInfoByContractFirm(String officeName, String contractFirm) throws Exception;
	
	public List<ContractorInfo> getContarctorInfoByOfficeName(String officeName) throws Exception;
	
	public ResponseEntity<String> editContractorInfo(ContractorInfo obj, String jwt) throws Exception;
	
	public List<String> getContractFirmByOfficeName(String officeName) throws Exception;

	public ContractorInfo getContractFirmByGodownName(String officeName, String godownName) throws Exception;
	
	public DataForEmdEntry getDataForEmdEntry(String officeName, String contractFirm, String gstCategory, String gstRate) throws Exception;
	
	public ResponseEntity<String> saveEmdDataForContractor(ContractorEmdEntryDto obj) throws Exception;
	
	public DataForRateUpdate getDataForRateUpdate(String officeName, String contractFirm) throws Exception;

	public ResponseEntity<String> saveRateUpdate(ContractorChargesData obj, Long id) throws Exception;

	public DataForEmdRefund getDataForEmdRefund(String officeName, String contractFirm) throws Exception;
	
	public ResponseEntity<String> saveEmdRefund(ContractorInfo obj, Long id) throws Exception;
	
	public DataForContractorStatus getDataForContractorStatus(String officeName, Integer year) throws Exception;
	
	public ResponseEntity<String> saveContractorStatus(ContractorStatusUpdateDto obj) throws Exception;

	public ResponseEntity<String> saveContractorStatus(Long id, String status) throws Exception;
	
	public DataForDistanceMapping getDataForDistanceMapping(String type, String officeName, String godownName, String district, String toRegion, String category) throws Exception;

	public ResponseEntity<String> saveDistanceMapData(DistanceMapping obj, String idNo) throws Exception;

//	public ResponseEntity<String> saveExistingDistanceMapData(DistanceMapping obj) throws Exception;

	public List<DistanceMappingAbstractTable> getDistanceMappingAbstractTableData(String region) throws Exception;
}
