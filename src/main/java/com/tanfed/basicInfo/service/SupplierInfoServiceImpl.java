package com.tanfed.basicInfo.service;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanfed.basicInfo.config.JwtTokenValidator;
import com.tanfed.basicInfo.entity.SupplierInfo;
import com.tanfed.basicInfo.repository.SupplierRepo;
import com.tanfed.basicInfo.response.DataForBillsPayableOb;

@Service
public class SupplierInfoServiceImpl implements SupplierInfoService {

	@Autowired
	private SupplierRepo supplierRepo;

	@Override
	public ResponseEntity<String> saveSupplierInfo(SupplierInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));

			SupplierInfo supplierInfoBySupplierName = supplierRepo.findBySupplierName(obj.getSupplierName());
			if (supplierInfoBySupplierName != null) {
				throw new FileAlreadyExistsException("Data already exists for SupplierName " + obj.getSupplierName());
			}

			supplierRepo.save(obj);
			return new ResponseEntity<>("Spplier Info Created", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public SupplierInfo getSupplierInfoBySupplierName(String supplierName) throws Exception {
		try {
			SupplierInfo supplierInfo = supplierRepo.findBySupplierName(supplierName);
			if (supplierInfo == null) {
				throw new FileNotFoundException("No supplier Info found for supplier Name" + supplierName);
			}
			return supplierInfo;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<SupplierInfo> getSupplierInfo() throws Exception {
		try {
			return supplierRepo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editSupplierInfo(SupplierInfo obj, String jwt) throws Exception {
		try {

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			SupplierInfo supplierInfo = supplierRepo.findById(obj.getId()).get();
			supplierInfo.getEmpId().add(empId);
			supplierInfo.setContact1(obj.getContact1());
			supplierInfo.setContact2(obj.getContact2());
			supplierInfo.setEmail(obj.getEmail());
			supplierInfo.setWebsite(obj.getWebsite());
			supplierInfo.setSupplierOf(obj.getSupplierOf());
			supplierRepo.save(supplierInfo);

			return new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getSupplierName(String activity) throws Exception {
		try {
			List<SupplierInfo> supplierInfobyOfficeName = supplierRepo.findAll();

			// if (supplierInfobyOfficeName == null) {
			// throw new FileNotFoundException("No data found");
			// }
			return supplierInfobyOfficeName.stream().filter(item -> item.getSupplierOf().contains(activity) && item.getRelationship().equals("Business"))
					.map(SupplierInfo::getSupplierName).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataForBillsPayableOb getDataForBillsPayableOb(String activity, String supplierName) throws Exception {
		try {
			DataForBillsPayableOb data = new DataForBillsPayableOb();
			if (activity != null && !activity.isEmpty()) {
				data.setSupplierNameList(getSupplierName(activity));
				if (supplierName != null && !supplierName.isEmpty()) {
					SupplierInfo supplierInfo = getSupplierInfoBySupplierName(supplierName);
					data.setDistrict(supplierInfo.getDistrict());
					data.setPincode(supplierInfo.getPincode());
					data.setStreet(supplierInfo.getStreet());
					data.setSupplierGst(supplierInfo.getSupplierGst());
				}
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<SupplierInfo> getSupplierInfo(String activity) {
		if (activity.isEmpty()) {
			return supplierRepo.findAll();
		} else {
			return supplierRepo.findAll().stream().filter(i -> i.getSupplierOf().contains(activity))
					.collect(Collectors.toList());
		}
	}

}
