package com.tanfed.basicInfo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.dto.LicenseDataDto;
import com.tanfed.basicInfo.entity.GodownInfo;
import com.tanfed.basicInfo.entity.LicenseData;
import com.tanfed.basicInfo.repository.LicenseRepo;

@Service
public class LicenseServiceImpl implements LicenseService {

	private Logger logger = LoggerFactory.getLogger(LicenseServiceImpl.class);

	@Autowired
	private LicenseRepo licenseRepo;

	@Autowired
	private GodownInfoService godownInfoService;

	@Autowired
	ObjectMapper mapper;

	@Override
	public ResponseEntity<String> saveLicense(String obj, MultipartFile files, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);

			logger.info("licenseData {}", obj);
			LicenseDataDto licenseDto = mapper.readValue(obj, LicenseDataDto.class);
			List<GodownInfo> godownList = new ArrayList<GodownInfo>();
			if (licenseDto.getGodownName() != null) {
				for(var i : licenseDto.getGodownName()) {
					godownList.add(godownInfoService.getGodownInfoByGodownName(i));					
				}

			}

			LicenseData license = new LicenseData(null, Arrays.asList(empId), licenseDto.getOfficeName(),
					files.getOriginalFilename(), files.getContentType(), files.getBytes(), licenseDto.getLicenseType(),
					licenseDto.getLicenseFor(), licenseDto.getLicenseNumber(), licenseDto.getValidFrom(),
					licenseDto.getValidTo());

			licenseRepo.save(license);

			return new ResponseEntity<String>("License added", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error : ", e);
			throw new Exception(e);
		}
	}

	@Override
	public List<LicenseData> getLicenseByOfficeName(String officeName) throws Exception {
		try {
			return licenseRepo.findByOfficeName(officeName);
		} catch (Exception e) {

//			log error in console
			logger.error("Error : ", e);

//			return error response
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editLicense(String obj, MultipartFile file, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			LicenseData license = mapper.readValue(obj, LicenseData.class);

			LicenseData licenseData = licenseRepo.findById(license.getId()).get();
			licenseData.getEmpId().add(empId);
			licenseData.setLicenseType(license.getLicenseType());
			licenseData.setLicenseFor(license.getLicenseFor());
			licenseData.setLicenseNumber(license.getLicenseNumber());
			licenseData.setValidFrom(license.getValidFrom());
			licenseData.setValidTo(license.getValidTo());
			licenseData.setFiledata(file.getBytes());
			licenseData.setFilename(file.getOriginalFilename());
			licenseData.setFiletype(file.getContentType());

//			save data obj in database
			licenseRepo.save(licenseData);

//			return response
			return new ResponseEntity<String>("License updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
//			log error in console
			logger.error("Error : ", e);

//			return error response
			throw new Exception(e);
		}
	}

	@Override
	public LicenseData getLicenseDataByLicenseNumber(String licenseNumber) throws Exception {
		try {
			return licenseRepo.findByLicenseNumber(licenseNumber);
		} catch (Exception e) {

//			log error in console
			logger.error("Error : ", e);

//			return error response
			throw new Exception(e);
		}
	}

}
