package com.tanfed.basicInfo.service;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.entity.DepartmentInfo;
import com.tanfed.basicInfo.repository.DepartmentRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<String> saveDepartmentInfo(DepartmentInfo obj, String jwt) throws Exception {
		try {

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));

			String officeCode = userService.getOfficeList().stream().filter(item -> {
				return item.getOfficeName().equals(obj.getOfficeName());
			}).map(com.tanfed.basicInfo.model.Office::getOfficeCode).collect(Collectors.toList()).get(0);

			obj.setOfficeCode(officeCode);

			departmentRepo.save(obj);

			return new ResponseEntity<>("Department Info Created", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DepartmentInfo getDaprtmentInfoByDeptName(String name) throws Exception {
		try {
			DepartmentInfo byDepartment = departmentRepo.findByDepartment(name);
			if (byDepartment == null) {
				throw new FileNotFoundException("No Buyer Info found for Dept name" + name);
			}

			return byDepartment;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<DepartmentInfo> getDaprtmentInfoByOfficeName(String officeName) throws Exception {
		try {
			List<DepartmentInfo> byOfficeName = departmentRepo.findByOfficeName(officeName);

			if (byOfficeName == null) {
				throw new FileNotFoundException("No godown Info found for office name" + officeName);
			}
			return byOfficeName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editDepartmentInfo(DepartmentInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);

			DepartmentInfo departmentInfo = departmentRepo.findById(obj.getId()).get();
			departmentInfo.getEmpId().add(empId);
			departmentInfo.setDoor(obj.getDoor());
			departmentInfo.setStreet(obj.getStreet());
			departmentInfo.setPincode(obj.getPincode());
			departmentInfo.setDistrict(obj.getDistrict());
			departmentInfo.setAuthorizedPerson(obj.getAuthorizedPerson());
			departmentInfo.setContact1(obj.getContact1());
			departmentInfo.setContact2(obj.getContact2());
			departmentInfo.setEmail(obj.getEmail());
			departmentInfo.setWebsite(obj.getWebsite());
			departmentInfo.setDepartment(obj.getDepartment());

			departmentRepo.save(departmentInfo);
			return new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getDepartmentNameByOfficeName(String officeName) throws Exception {
		try {
			List<DepartmentInfo> byOfficeName = departmentRepo.findByOfficeName(officeName);
			if (byOfficeName == null) {
				throw new FileNotFoundException("No godown Info found for office name" + officeName);
			}
			return byOfficeName.stream().map(DepartmentInfo::getDepartment).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
