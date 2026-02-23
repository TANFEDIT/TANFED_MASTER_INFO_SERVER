package com.tanfed.basicInfo.service;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.controller.DataController;
import com.tanfed.basicInfo.entity.GodownInfo;
import com.tanfed.basicInfo.model.Office;
import com.tanfed.basicInfo.repository.GodownRepo;
import com.tanfed.basicInfo.response.DataForGodownInfo;

@Service
public class GodownInfoServiceImpl implements GodownInfoService {

	@Autowired
	private GodownRepo godownRepo;

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<String> saveGodownInfo(GodownInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));
			String officeCode = userService.getOfficeList().stream().filter(item -> {
				return item.getOfficeName().equals(obj.getOfficeName());
			}).map(Office::getOfficeCode).collect(Collectors.toList()).get(0);
			obj.setOfficeCode(officeCode);
			obj.getInsurance().forEach(i -> {
				i.setGodown(obj);
			});
			godownRepo.save(obj);
			return new ResponseEntity<>("Godown Info Created", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public GodownInfo getGodownInfoByGodownName(String godownName) throws Exception {
		try {
			GodownInfo godownInfo = godownRepo.findByGodownName(godownName);
			if (godownInfo == null) {
				throw new FileNotFoundException("No godown Info found for godown name" + godownName);
			}
			return godownInfo;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<GodownInfo> getGodownInfoByOfficeName(String officeName) throws Exception {
		try {
			List<GodownInfo> godownInfoByOfficeName = godownRepo.findByOfficeName(officeName);
			if (godownInfoByOfficeName == null) {
				throw new FileNotFoundException("No godown Info found for office name" + officeName);
			}
			return godownInfoByOfficeName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editGodownInfo(GodownInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			GodownInfo godownInfo = godownRepo.findById(obj.getId()).get();

			godownInfo.setTotalCapacity(obj.getTotalCapacity());
			godownInfo.setNumberOfGodowns(obj.getNumberOfGodowns());
			godownInfo.setCapacities(obj.getCapacities());
			godownInfo.setKeeperName(obj.getKeeperName());
			godownInfo.setContactNo1(obj.getContactNo1());
			godownInfo.setContactNo2(obj.getContactNo2());
			godownInfo.setGkDesignation(obj.getGkDesignation());
			godownInfo.getEmpId().add(empId);
			godownRepo.save(godownInfo);

			return new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getGodownNameByOfficeName(String officeName) throws Exception {
		try {
			List<GodownInfo> godownInfoByOfficeName = godownRepo.findByOfficeName(officeName);

			if (godownInfoByOfficeName == null) {
				throw new FileNotFoundException("No godown Info found for office name" + officeName);
			}
			return godownInfoByOfficeName.stream().map(GodownInfo::getGodownName).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private DataController dataController;

	@Autowired
	private LicenseService licenseService;

	private static Logger logger = LoggerFactory.getLogger(GodownInfoServiceImpl.class);

	@Override
	public DataForGodownInfo getDataForGodownInfo(String officeName, String district, String block) throws Exception {
		try {
			logger.info(officeName);
			DataForGodownInfo data = new DataForGodownInfo();
			data.setDistrictData(dataController.getDistrictData(district, block, officeName));

			data.setLicenseNoList(licenseService.getLicenseByOfficeName(officeName).stream()
					.map(item -> item.getLicenseNumber()).collect(Collectors.toList()));

			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
