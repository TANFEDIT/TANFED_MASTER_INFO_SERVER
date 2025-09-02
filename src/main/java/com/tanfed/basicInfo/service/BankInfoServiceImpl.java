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
import com.tanfed.basicInfo.entity.BankInfo;
import com.tanfed.basicInfo.repository.BankRepo;
import com.tanfed.basicInfo.response.DataForOB;

@Service
public class BankInfoServiceImpl implements BankInfoService {

	@Autowired
	private BankRepo bankRepo;

	@Override
	public ResponseEntity<String> saveBankInfo(BankInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));
			bankRepo.save(obj);
			return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<BankInfo> getBankInfoByOfficeName(String officeName) throws Exception {
		try {
			List<BankInfo> byOfficeName = bankRepo.findByOfficeName(officeName);
			if (byOfficeName == null) {
				throw new FileNotFoundException("No Bank Info found for office name" + officeName);
			}
			return byOfficeName;

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editBankInfo(BankInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			BankInfo bankInfo = bankRepo.findById(obj.getId()).get();
			bankInfo.getEmpId().add(empId);
			bankInfo.setDoor(obj.getDoor());
			bankInfo.setStreet(obj.getStreet());
			bankInfo.setDistrict(obj.getDistrict());
			bankInfo.setPincode(obj.getPincode());
			bankInfo.setContact1(obj.getContact1());
			bankInfo.setContact2(obj.getContact2());
			bankInfo.setEmail(obj.getEmail());
			bankInfo.setWebsite(obj.getWebsite());

			bankRepo.save(bankInfo);

			return new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataForOB getDataForOBForm(String officeName, String bankName, String branchName, String accountType)
			throws Exception {
		try {
			DataForOB data = new DataForOB();
			if (!officeName.isEmpty() && officeName != null) {
				List<BankInfo> bankInfoByOfficeName = getBankInfoByOfficeName(officeName);
				data.setBankList(bankInfoByOfficeName.stream().map(BankInfo::getBankName).collect(Collectors.toSet()));
				if (!bankName.isEmpty() && bankName != null) {
					data.setAccTypeList(
							bankInfoByOfficeName.stream().filter(item -> item.getBankName().equals(bankName))
									.map(BankInfo::getAccountType).collect(Collectors.toSet()));
					if (!accountType.isEmpty() && accountType != null) {
						data.setBranchNameList(bankInfoByOfficeName.stream()
								.filter(item -> item.getBankName().equals(bankName)
										&& item.getAccountType().equals(accountType))
								.map(BankInfo::getBranchName).collect(Collectors.toSet()));

						if (!branchName.isEmpty() && branchName != null) {
							data.setAccNoList(bankInfoByOfficeName.stream()
									.filter(item -> item.getBankName().equals(bankName)
											&& item.getAccountType().equals(accountType)
											&& item.getBranchName().equals(branchName))
									.map(BankInfo::getAccountNumber).collect(Collectors.toList()));
						}

					}
				}
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public BankInfo getBankInfoByAccountNo(Long accountNumber) throws Exception {
		try {
			return bankRepo.findByAccountNumber(accountNumber);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
