package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.GodownInfo;
import com.tanfed.basicInfo.response.DataForGodownInfo;

public interface GodownInfoService {

	/*post mapping for godown info
	 * saves obj to database and returns response string and HttpStatus.CREATED*/
	public ResponseEntity<String> saveGodownInfo(GodownInfo obj, String jwt) throws Exception;
	
	public GodownInfo getGodownInfoByGodownName(String godownName) throws Exception;
	
	public List<GodownInfo> getGodownInfoByOfficeName(String officeName) throws Exception;
	
	public ResponseEntity<String> editGodownInfo(GodownInfo obj, String jwt) throws Exception;
	
	public List<String> getGodownNameByOfficeName(String officeName) throws Exception;
	
	public DataForGodownInfo getDataForGodownInfo(String officeName, String district, String block, 
			String licenseNo) throws Exception;
}
