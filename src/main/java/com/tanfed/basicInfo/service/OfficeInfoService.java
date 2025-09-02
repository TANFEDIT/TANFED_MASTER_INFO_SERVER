package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.OfficeInfo;
import com.tanfed.basicInfo.response.DataForOfficeForm;

public interface OfficeInfoService {

	public ResponseEntity<String> saveOfficeInfo(OfficeInfo obj, String jwt) throws Exception;
	
	public OfficeInfo getOfficeInfoByOfficeName(String officeName) throws Exception;
	
	public List<OfficeInfo> getOfficeList() throws Exception;
	
	public ResponseEntity<String> editOfficeInfo(OfficeInfo obj, String jwt) throws Exception;
	
	public DataForOfficeForm getDataForOfficeForm(String officeType) throws Exception; 
	
}
