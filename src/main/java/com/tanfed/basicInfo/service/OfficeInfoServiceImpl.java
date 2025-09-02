package com.tanfed.basicInfo.service;

import java.nio.file.FileAlreadyExistsException;
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
import com.tanfed.basicInfo.entity.OfficeInfo;
import com.tanfed.basicInfo.model.Office;
import com.tanfed.basicInfo.repository.OfficeInfoRepo;
import com.tanfed.basicInfo.response.DataForOfficeForm;

@Service
public class OfficeInfoServiceImpl implements OfficeInfoService {

	private Logger logger = LoggerFactory.getLogger(OfficeInfoServiceImpl.class);

	@Autowired
	private OfficeInfoRepo officeInfoRepo;

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<String> saveOfficeInfo(OfficeInfo obj, String jwt) throws Exception {
		try {
			OfficeInfo officeInfoByOfficeName = getOfficeInfoByOfficeName(obj.getOfficeName());
			if (officeInfoByOfficeName != null) {
				throw new FileAlreadyExistsException("Data already exists for officeName " + obj.getOfficeName());
			}

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));

			String officeCode = userService.getOfficeList().stream().filter(item -> {
				return item.getOfficeName().equals(obj.getOfficeName());
			}).map(Office::getOfficeCode).collect(Collectors.toList()).get(0);
			obj.setOfficeCode(officeCode);

			officeInfoRepo.save(obj);
			return new ResponseEntity<>("Office Info Created", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		}
	}

	@Override
	public OfficeInfo getOfficeInfoByOfficeName(String officeName) throws Exception {
		try {
			logger.info("service{}", officeName);

			return officeInfoRepo.findByOfficeName(officeName);
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		}
	}

	@Override
	public List<OfficeInfo> getOfficeList() throws Exception {
		try {

			return officeInfoRepo.findAll();
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editOfficeInfo(OfficeInfo obj, String jwt) throws Exception {
		try {

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			OfficeInfo officeInfo = officeInfoRepo.findById(obj.getId()).get();

			officeInfo.setOfficeType(obj.getOfficeType());
			officeInfo.setOfficeName(obj.getOfficeName());
			officeInfo.setAreaOperation(obj.getAreaOperation());
			officeInfo.setDistrict(obj.getDistrict());
			officeInfo.setDistrictList(obj.getDistrictList());
			officeInfo.setDoor(obj.getDoor());
			officeInfo.setIsUnitOffice(obj.getIsUnitOffice());
			officeInfo.setManager(obj.getManager());
			officeInfo.setOrganization(obj.getOrganization());
			officeInfo.setPincode(obj.getPincode());
			officeInfo.setProductionUnit(obj.getProductionUnit());
			officeInfo.setServiceUnit(obj.getServiceUnit());
			officeInfo.setStreet(obj.getStreet());
			officeInfo.setUnitOfficeType(obj.getUnitOfficeType());
			officeInfo.setContactName(obj.getContactName());
			officeInfo.setContactNo1(obj.getContactNo1());
			officeInfo.setContactNo2(obj.getContactNo2());
			officeInfo.setEmail(obj.getEmail());
			officeInfo.getEmpId().add(empId);

			officeInfoRepo.save(officeInfo);

			return new ResponseEntity<>("Office Info Updated", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		}
	}

	@Autowired
	private DataController controller;

	@Override
	public DataForOfficeForm getDataForOfficeForm(String officeType) throws Exception {
		try {
			return new DataForOfficeForm(controller.getOfficeListHandler(officeType),
					controller.getDistrictListHandler());
		} catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		}
	}

}
