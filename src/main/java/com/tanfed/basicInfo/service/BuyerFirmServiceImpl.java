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
import com.tanfed.basicInfo.entity.BankInfo;
import com.tanfed.basicInfo.entity.BuyerFirmInfo;
import com.tanfed.basicInfo.model.Office;
import com.tanfed.basicInfo.repository.BuyerFirmRepo;
import com.tanfed.basicInfo.response.BuyerFirmData;
import com.tanfed.basicInfo.response.DataForBillsReceivablesOb;
import com.tanfed.basicInfo.response.DataForBuyerFirm;
import com.tanfed.basicInfo.response.DistrictData;

@Service
public class BuyerFirmServiceImpl implements BuyerFirmService {

	@Autowired
	private BuyerFirmRepo buyerFirmRepo;

	private static Logger logger = LoggerFactory.getLogger(BuyerFirmServiceImpl.class);

	@Override
	public ResponseEntity<String> saveBuyerFirmInfo(BuyerFirmInfo obj, String jwt) throws Exception {
		try {
			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);
			obj.setEmpId(Arrays.asList(empId));
			buyerFirmRepo.save(obj);
			return new ResponseEntity<>("BuyerInfo Created", HttpStatus.CREATED);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public BuyerFirmInfo getBuyerFirmByFirmName(String nameOfInstitution) throws Exception {
		try {
			BuyerFirmInfo byFirmName = buyerFirmRepo.findByNameOfInstitution(nameOfInstitution);
			if (byFirmName == null) {
				throw new FileNotFoundException("No Buyer Info found for IfmsId" + nameOfInstitution);
			}
			return byFirmName;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private UserService userService;

	@Autowired
	private BankInfoService bankInfoService;

	@Override
	public BuyerFirmData getBuyerFirmByOfficeName(String officeName, String district, String bankName)
			throws Exception {
		try {
			BuyerFirmData data = new BuyerFirmData();
			data.setOfficeList(
					userService.getOfficeList().stream().map(Office::getOfficeName).collect(Collectors.toList()));
			if (officeName != null && !officeName.isEmpty()) {
				List<BuyerFirmInfo> byOfficeName = buyerFirmRepo.findByOfficeName(officeName);
				if (byOfficeName == null) {
					throw new FileNotFoundException("No godown Info found for office name" + officeName);
				}
				data.setDistrictList(byOfficeName.stream().map(BuyerFirmInfo::getDistrict).collect(Collectors.toSet()));
				if (district != null && !district.isEmpty()) {
					data.setBuyerData(byOfficeName.stream().filter(item -> item.getDistrict().equals(district))
							.collect(Collectors.toList()));
				}
			}

			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private DataController dataController;

	@Override
	public DataForBuyerFirm getDataForBuyerFirm(String officeName, String block, String district, String bankName)
			throws Exception {
		try {
			DataForBuyerFirm data = new DataForBuyerFirm();

			if (officeName != null && !officeName.isEmpty()) {
				DistrictData districtData = dataController.getDistrictData(district, block, officeName);
				data.setDistrictData(districtData);
				List<BankInfo> bankInfoByOfficeName = bankInfoService.getBankInfoByOfficeName().stream()
						.filter(i -> i.getOfficeName().equals(officeName)).collect(Collectors.toList());
				data.setBankNameList(
						bankInfoByOfficeName.stream().map(BankInfo::getBankName).collect(Collectors.toSet()));
				if (bankName != null && !bankName.isEmpty()) {
					data.setBranchNameList(bankInfoByOfficeName.stream()
							.filter(item -> item.getBankName().equals(bankName)
									&& item.getAccountType().equals("Non PDS A/c Fert"))
							.map(BankInfo::getBranchName).collect(Collectors.toSet()));
				}
			}
			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public ResponseEntity<String> editBuyerFirm(BuyerFirmInfo obj, String jwt) throws Exception {
		try {

			String empId = JwtTokenValidator.getEmailFromJwtToken(jwt);

			BuyerFirmInfo buyerFirmInfo = buyerFirmRepo.findById(obj.getId()).get();
			buyerFirmInfo.getEmpId().add(empId);

			buyerFirmInfo.setBusinessWithTanfed(obj.getBusinessWithTanfed());
			buyerFirmInfo.setBankName(obj.getBankName());
			buyerFirmInfo.setBranchName(obj.getBranchName());
			buyerFirmInfo.setLicenceNo(obj.getLicenceNo());
			buyerFirmInfo.setValidityDateFrom(obj.getValidityDateFrom());
			buyerFirmInfo.setValidityDateTo(obj.getValidityDateTo());
			buyerFirmInfo.setAddress(obj.getAddress());
			buyerFirmInfo.setContact1(obj.getContact1());
			buyerFirmInfo.setContact2(obj.getContact2());
			buyerFirmInfo.setEmailId(obj.getEmailId());
			buyerFirmInfo.setBankOperation(obj.getBankOperation());
			buyerFirmInfo.setBuyerGstNo(obj.getBuyerGstNo());

			buyerFirmRepo.save(buyerFirmInfo);

			return new ResponseEntity<>("Updated Successfully", HttpStatus.ACCEPTED);

		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<String> getBuyerNameByOfficeName(String officeName) throws Exception {
		try {
			List<BuyerFirmInfo> byOfficeName = buyerFirmRepo.findByOfficeName(officeName);

			if (byOfficeName == null) {
				throw new FileNotFoundException("No godown Info found for office name" + officeName);
			}
			return byOfficeName.stream().map(BuyerFirmInfo::getNameOfInstitution).collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<BuyerFirmInfo> getBuyerInfo() throws Exception {
		try {
			return buyerFirmRepo.findAll();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<BuyerFirmInfo> getBuyerInfoByOfficeName(String officeName) throws Exception {
		try {
			return buyerFirmRepo.findByOfficeName(officeName);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public DataForBillsReceivablesOb getDataForBillsReceivablesOb(String firmType, String nameOfInstitution,
			String officeName) throws Exception {
		try {
			DataForBillsReceivablesOb data = new DataForBillsReceivablesOb();
			if (officeName != null && !officeName.isEmpty()) {
				if (firmType != null && !firmType.isEmpty()) {
					logger.info(officeName);
					data.setBuyerNameList(buyerFirmRepo.findByOfficeName(officeName).stream()
							.filter(i -> i.getFirmType().equals(firmType)).map(BuyerFirmInfo::getNameOfInstitution)
							.collect(Collectors.toList()));
					if (nameOfInstitution != null && !nameOfInstitution.isEmpty()) {
						BuyerFirmInfo buyerFirmInfo = buyerFirmRepo.findByOfficeName(officeName).stream()
								.filter(i -> i.getNameOfInstitution().equals(nameOfInstitution))
								.reduce((first, second) -> second).orElse(null);
						data.setIfmsId(buyerFirmInfo.getIfmsIdNo());
						data.setAddress(buyerFirmInfo.getAddress());
						data.setDistrict(buyerFirmInfo.getDistrict());
						data.setBuyerGstNo(buyerFirmInfo.getBuyerGstNo());
					}
				}
			}

			return data;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
