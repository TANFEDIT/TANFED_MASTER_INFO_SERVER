package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.tanfed.basicInfo.entity.LicenseData;

public interface LicenseService {

	public ResponseEntity<String> saveLicense(String obj, MultipartFile[] files, String jwt) throws Exception;
	
	public List<LicenseData> getLicenseByOfficeName(String officeName) throws Exception;
	
	public ResponseEntity<String> editLicense(String obj, MultipartFile file, String jwt) throws Exception;
	
	public LicenseData getLicenseDataByLicenseNumber(String licenseNumber) throws Exception;
}
