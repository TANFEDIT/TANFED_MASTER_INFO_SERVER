package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.DepartmentInfo;

public interface DepartmentService {

	public ResponseEntity<String> saveDepartmentInfo(DepartmentInfo obj, String jwt) throws Exception;
	
	public DepartmentInfo getDaprtmentInfoByDeptName(String name) throws Exception;
	
	public List<DepartmentInfo> getDaprtmentInfoByOfficeName(String officeName) throws Exception;
	
	public ResponseEntity<String> editDepartmentInfo(DepartmentInfo obj, String jwt) throws Exception;
	
	public List<String> getDepartmentNameByOfficeName(String officeName) throws Exception;
}
