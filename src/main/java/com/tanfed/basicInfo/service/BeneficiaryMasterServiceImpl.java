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
import com.tanfed.basicInfo.entity.BeneficiaryMaster;
import com.tanfed.basicInfo.repository.BeneficiaryMasterRepo;

@Service
public class BeneficiaryMasterServiceImpl implements BeneficiaryMasterService {

	@Autowired
	private BeneficiaryMasterRepo beneficiaryMasterRepo;

	@Override
	public ResponseEntity<String> saveBeneficiaryMaster(List<BeneficiaryMaster> obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);

			obj.forEach(item -> item.setEmpId(Arrays.asList(empId)));

			beneficiaryMasterRepo.saveAll(obj);

			return new ResponseEntity<String>("Created Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<BeneficiaryMaster> getBeneficiaryMasterByName(String beneficiaryName, String officeName)
			throws Exception {
		try {
			List<BeneficiaryMaster> byBeneficiaryName = beneficiaryMasterRepo
					.findByBeneficiaryNameAndOfficeName(beneficiaryName, officeName);
			if (byBeneficiaryName == null) {
				throw new FileNotFoundException("No data found");
			}
			return byBeneficiaryName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private SupplierInfoService supplierInfoService;

	@Override
	public List<String> fetchDataForBeneficiaryMaster(String officeName) throws Exception {
		try {
			if (officeName != null && !officeName.isEmpty() && officeName.equals("Head Office")) {
				return supplierInfoService.getSupplierInfo("").stream().map(item -> item.getSupplierName())
						.collect(Collectors.toList());
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<BeneficiaryMaster> getBeneficiaryListByOfficeName(String officeName) throws Exception {
		try {
			List<BeneficiaryMaster> byOfficeName = beneficiaryMasterRepo.findByOfficeName(officeName);
			if (byOfficeName == null) {
				throw new FileNotFoundException("No data found");
			}
			return byOfficeName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editBeneficiaryMaster(BeneficiaryMaster obj, String jwt) throws Exception {
		try {
			BeneficiaryMaster beneficiaryMaster = beneficiaryMasterRepo.findById(obj.getId()).get();

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);

			beneficiaryMaster.getEmpId().add(empId);
			beneficiaryMaster.setBeneficiaryName(obj.getBeneficiaryName());
			beneficiaryMaster.setBankName(obj.getBankName());
			beneficiaryMaster.setGstNo(obj.getGstNo());
			beneficiaryMaster.setAccountNo(obj.getAccountNo());
			beneficiaryMaster.setPanNo(obj.getPanNo());
			beneficiaryMaster.setIfscCode(obj.getIfscCode());
			beneficiaryMaster.setAccountType(obj.getAccountType());
			beneficiaryMaster.setBeneficiaryApplicableToHoAccount(obj.getBeneficiaryApplicableToHoAccount());

			beneficiaryMasterRepo.save(beneficiaryMaster);
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getBeneficiaryNameListByOfficeName(String officeName) throws Exception {
		try {
			return getBeneficiaryListByOfficeName(officeName).stream().map(BeneficiaryMaster::getBeneficiaryName)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
