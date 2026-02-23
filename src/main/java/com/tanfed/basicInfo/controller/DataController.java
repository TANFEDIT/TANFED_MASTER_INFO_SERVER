package com.tanfed.basicInfo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanfed.basicInfo.entity.BankInfo;
import com.tanfed.basicInfo.entity.BeneficiaryMaster;
import com.tanfed.basicInfo.entity.BuyerFirmInfo;
import com.tanfed.basicInfo.entity.ContractorInfo;
import com.tanfed.basicInfo.entity.GodownInfo;
import com.tanfed.basicInfo.entity.LicenseData;
import com.tanfed.basicInfo.entity.OfficeInfo;
import com.tanfed.basicInfo.entity.ProductConfig;
import com.tanfed.basicInfo.entity.ProductMaster;
import com.tanfed.basicInfo.entity.SupplierInfo;
import com.tanfed.basicInfo.entity.TaxInfo;
import com.tanfed.basicInfo.entity.Terms_Price_Config;
import com.tanfed.basicInfo.model.Office;
import com.tanfed.basicInfo.repository.BankRepo;
import com.tanfed.basicInfo.repository.BeneficiaryMasterRepo;
import com.tanfed.basicInfo.repository.BuyerFirmRepo;
import com.tanfed.basicInfo.repository.ContractorInfoRepo;
import com.tanfed.basicInfo.repository.GodownRepo;
import com.tanfed.basicInfo.repository.LicenseRepo;
import com.tanfed.basicInfo.repository.OfficeInfoRepo;
import com.tanfed.basicInfo.repository.ProductConfigRepo;
import com.tanfed.basicInfo.repository.ProductMasterRepo;
import com.tanfed.basicInfo.repository.SupplierRepo;
import com.tanfed.basicInfo.repository.TaxInfoRepo;
import com.tanfed.basicInfo.repository.Terms_Price_Config_Repo;
import com.tanfed.basicInfo.response.DistrictData;
import com.tanfed.basicInfo.service.DistrictService;
import com.tanfed.basicInfo.service.OfficeInfoService;
import com.tanfed.basicInfo.service.UserService;

@RestController
@RequestMapping("/api/data")
public class DataController {

	@Autowired
	private UserService userService;

	@GetMapping("/getofficeList")
	public List<String> getOfficeListHandler(@RequestParam String officeType) throws Exception {
		try {
			if (officeType.isEmpty()) {
				return userService.getOfficeList().stream().map(Office::getOfficeName).collect(Collectors.toList());
			} else {
				return userService.getOfficeList().stream().filter(item -> item.getOfficeType().equals(officeType))
						.map(Office::getOfficeName).collect(Collectors.toList());
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Autowired
	private DistrictService districtService;

	@Autowired
	private OfficeInfoService officeInfoService;

	@GetMapping("/fetchdistrictdata")
	public DistrictData getDistrictData(@RequestParam(required = false) String district,
			@RequestParam(required = false) String block, @RequestParam(required = false) String officeName)
			throws Exception {
		try {
			DistrictData response = new DistrictData();

			if (officeName.equals("Head Office") || getOfficeListHandler("Unit Office").contains(officeName)) {
				response.setDistrictList(getDistrictListHandler().stream().collect(Collectors.toList()));
			} else {
				response.setDistrictList(officeInfoService.getOfficeInfoByOfficeName(officeName).getDistrictList());
			}
			return response;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping("/fetchdistrict")
	public List<String> getDistrictListHandler() throws Exception {
		return districtService.getDistrictList();
	}

	@Autowired
	private Terms_Price_Config_Repo terms_Price_Config_Repo;

	@Autowired
	private TaxInfoRepo taxInfoRepo;

	@Autowired
	private ProductConfigRepo productConfigRepo;

	@Autowired
	private ProductMasterRepo productMasterRepo;

	@Autowired
	private BeneficiaryMasterRepo beneficiaryMasterRepo;

	@Autowired
	private OfficeInfoRepo officeInfoRepo;

	@Autowired
	private SupplierRepo supplierRepo;

	@Autowired
	private LicenseRepo licenseRepo;

	@Autowired
	private BankRepo bankRepo;

	@Autowired
	private GodownRepo godownRepo;

	@Autowired
	private BuyerFirmRepo buyerFirmRepo;
	
	@Autowired
	private ContractorInfoRepo contractorInfoRepo;

	@GetMapping("/gettcconfig")
	public List<Terms_Price_Config> getTerms_Price_Config() {
		return terms_Price_Config_Repo.findAll();
	}

	@GetMapping("/gettaxinfo")
	public List<TaxInfo> getTaxInfo() {
		return taxInfoRepo.findAll();
	}

	@GetMapping("/getproductconfig")
	public List<ProductConfig> getProductConfig() {
		return productConfigRepo.findAll();
	}

	@GetMapping("/getproductMaster")
	public List<ProductMaster> getproductMaster() {
		return productMasterRepo.findAll();
	}

	@GetMapping("/getbeneficiaryMaster")
	public List<BeneficiaryMaster> getbeneficiaryMaster() {
		return beneficiaryMasterRepo.findAll();
	}

	@GetMapping("/getOfficeInfo")
	public List<OfficeInfo> getOfficeInfo() {
		return officeInfoRepo.findAll();
	}

	@GetMapping("/getSupplierInfo")
	public List<SupplierInfo> getSupplierInfo() {
		return supplierRepo.findAll();
	}

	@GetMapping("/getLicenseData")
	public List<LicenseData> getLicenseData() {
		return licenseRepo.findAll();
	}

	@GetMapping("/getBankInfo")
	public List<BankInfo> getBankInfo() {
		return bankRepo.findAll();
	}

	@GetMapping("/getGodownInfo")
	public List<GodownInfo> getGodownInfo() {
		return godownRepo.findAll();
	}

	@GetMapping("/getBuyerFirmInfo")
	public List<BuyerFirmInfo> getBuyerFirmInfo() {
		return buyerFirmRepo.findAll();
	}
	
	@GetMapping("/getContractorInfo")
	public List<ContractorInfo> getContractorInfo() {
		return contractorInfoRepo.findAll();
	}

}
