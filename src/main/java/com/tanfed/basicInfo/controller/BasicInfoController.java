package com.tanfed.basicInfo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tanfed.basicInfo.dto.RateUpdateDto;
import com.tanfed.basicInfo.entity.*;
import com.tanfed.basicInfo.model.*;
import com.tanfed.basicInfo.repository.DistanceMappingRepo;
import com.tanfed.basicInfo.repository.TaxInfoRepo;
import com.tanfed.basicInfo.response.*;
import com.tanfed.basicInfo.service.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/basic-info")
public class BasicInfoController {

	private Logger logger = LoggerFactory.getLogger(BasicInfoController.class);

	/*
	 * BASIC INFO office form API only used by head office user with role used
	 * in @PreAuthorize SUPERADMIN and ESTADMIN are permitted to access this API
	 */
	@Autowired
	private OfficeInfoService officeInfoService;

	/*
	 * @PostMapping for office form saves OfficeInfo object @RequestBody to database
	 * and returns ResponseEntity<?> HttpStatus.CREATED and String Response if error
	 * occurred throws exception only calls service after authenticated
	 */
	@PostMapping("/saveoffice")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ESTADMIN')")
	public ResponseEntity<String> saveOfficeInfoHandler(@RequestBody OfficeInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return officeInfoService.saveOfficeInfo(obj, jwt);
	}

	/*
	 * @PutMapping for office form saves updated OfficeInfo object @RequestBody to
	 * database after fetching it by id returns ResponseEntity<?>
	 * HttpStatus.ACCEPTED and String Response if error occurred throws exception
	 * and only calls service authenticated
	 */
	@PutMapping("/editoffice")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ESTADMIN')")
	public ResponseEntity<String> editOfficeInfoHandler(@RequestBody OfficeInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return officeInfoService.editOfficeInfo(obj, jwt);
	}

	/*
	 * @get for office form returns list of OfficeInfo object if error occurred
	 * throws exception and only calls service authenticated
	 */
	@GetMapping("/fetchofficedatalist")
	public List<OfficeInfo> getOfficeListHandler() throws Exception {
		return officeInfoService.getOfficeList();
	}

	/*
	 * @get for office form returns OfficeInfo object fetched by
	 * OfficeName @RequestParam if error occurred throws exception and only calls
	 * service authenticated
	 */
	@GetMapping("/fetchofficedata")
	public OfficeInfo getOfficeInfoByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return officeInfoService.getOfficeInfoByOfficeName(officeName);
	}

	@Autowired
	private UserService userService;

	private ResponseEntity<User> fetchUserHandler;

	@Autowired
	private TaxInfoRepo taxInfoRepo;

	@GetMapping("/officedataheader")
	public OfficeHeader getOfficeHeader(@RequestHeader("Authorization") String jwt) throws Exception {
		try {
			fetchUserHandler = userService.fetchUserHandler(jwt);
			User user = fetchUserHandler.getBody();
			if (user == null) {
				return null;
			}
			OfficeInfo officeInfo = getOfficeInfoByOfficeNameHandler(user.getOfficeName());
			TaxInfo taxInfo = taxInfoRepo.findById(1L).orElse(null);

			String officeType = officeInfo != null ? officeInfo.getOfficeType() : null;
			String door = officeInfo != null ? officeInfo.getDoor() : null;
			String street = officeInfo != null ? officeInfo.getStreet() : null;
			String district = officeInfo != null ? officeInfo.getDistrict() : null;
			Integer pincode = officeInfo != null ? officeInfo.getPincode() : null;
			String gstNo = taxInfo != null ? taxInfo.getGstNo() : null;
			String tanNo = taxInfo != null ? taxInfo.getTanNo() : null;
			
			return new OfficeHeader(user.getEmpId(), user.getRole(), user.getEmpName(), user.getDesignation(), user.getOfficeName(), officeType,
					door, street, district, pincode, gstNo, tanNo, user.getImgName(), user.getImgType(), user.getImgData()
			);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}


	@GetMapping("/fetchdataforofficeform")
	public DataForOfficeForm getDataForOfficeFormHandler(@RequestParam String officeType) throws Exception {
		return officeInfoService.getDataForOfficeForm(officeType);
	}

	@Autowired
	private LicenseService licenseService;

	@PostMapping(value = "/saveLicense", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> saveLicenseHandler(@RequestPart String obj, @RequestParam MultipartFile[] files,
			@RequestHeader("Authorization") String jwt) throws Exception {
		logger.info(obj);
		return licenseService.saveLicense(obj, files, jwt);
	}

	@PutMapping("/editlicense")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> editLicenseHandler(@RequestParam String obj, @RequestParam MultipartFile file,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return licenseService.editLicense(obj, file, jwt);
	}

	@GetMapping("/fetchlicensebyoffice")
	public List<LicenseData> getLicenseByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return licenseService.getLicenseByOfficeName(officeName);
	}

	/*
	 * BASIC INFO Supplier form API only used by head office user with role used
	 * in @PreAuthorize SUPERADMIN, MARKADMIN, FERTADMIN, SPAIADMIN are permitted to
	 * access this API
	 */
	@Autowired
	private SupplierInfoService supplierInfoService;

	/*
	 * @PostMapping for supplier form saves SupplierInfo object to database and
	 * returns ResponseEntity<?> HttpStatus.CREATED and String Response if error
	 * occurred throws exception only calls service after authenticated
	 */
	@PostMapping("/savesupplier")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> saveSupplierInfoHandler(@RequestBody SupplierInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return supplierInfoService.saveSupplierInfo(obj, jwt);
	}

	/*
	 * @get for supplier form returns supplier object fetched by supplier name if
	 * error occurred throws exception and calls service after authentication
	 */
	@GetMapping("/fetchsupplierdata")
	public SupplierInfo getSupplierInfoBySupplierNameHandler(@RequestParam String supplierName) throws Exception {
		return supplierInfoService.getSupplierInfoBySupplierName(supplierName);
	}

	@GetMapping("/fetchsupplierdatalist")
	public List<SupplierInfo> getSupplierInfoHandler(String activity) throws Exception {
		return supplierInfoService.getSupplierInfo(activity);
	}

	@GetMapping("/fetchsuppliernamelist")
	public List<String> getSupplierNameHadnler(String activity) throws Exception {
		return supplierInfoService.getSupplierName(activity);
	}

	@GetMapping("/fetchsupplierdataforbills")
	public DataForBillsPayableOb getDataForBillsPayableObHandler(@RequestParam String activity,
			@RequestParam String supplierName) throws Exception {
		return supplierInfoService.getDataForBillsPayableOb(activity, supplierName);
	}

	@PutMapping("/editsupplier")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> editSupplierInfoHandler(@RequestBody SupplierInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return supplierInfoService.editSupplierInfo(obj, jwt);
	}

	/*
	 * BASIC INFO forms API only used by regional office user Godown, Bank, Buyer
	 * Firm, Contractor, Department above forms have common permissions with roles
	 * used in @PreAuthorize SUPERADMIN and ROADMIN are permitted to access this API
	 * 
	 * godown
	 */
	@Autowired
	private GodownInfoService godownInfoService;

	@PostMapping("/savegodown")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> saveGodownInfoHandler(@RequestBody GodownInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return godownInfoService.saveGodownInfo(obj, jwt);
	}

	@GetMapping("/fetchgodowndata")
	public GodownInfo getGodownInfoByGodownNameHandler(String godownName) throws Exception {
		return godownInfoService.getGodownInfoByGodownName(godownName);
	}

	@GetMapping("/fetchgodowndatalist")
	public List<GodownInfo> getGodownInfoByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return godownInfoService.getGodownInfoByOfficeName(officeName);
	}

	@GetMapping("/fetchgodownnamelist")
	public List<String> getGodownNameByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return godownInfoService.getGodownNameByOfficeName(officeName);
	}

	@PutMapping("/editgodown")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> editGodownInfoHandler(@RequestBody GodownInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return godownInfoService.editGodownInfo(obj, jwt);
	}

	@GetMapping("/fetchdataforgodown")
	public DataForGodownInfo getDataForGodownInfoHandler(@RequestParam String officeName, String district, String block,
			String licenseNo) throws Exception {
		return godownInfoService.getDataForGodownInfo(officeName, district, block, licenseNo);
	}

	/* Bank */
	@Autowired
	private BankInfoService bankInfoService;

	@PostMapping("/savebank")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ACCADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> saveBankInfo(@RequestBody BankInfo obj, @RequestHeader("Authorization") String jwt)
			throws Exception {
		return bankInfoService.saveBankInfo(obj, jwt);
	}
	
	@PostMapping("/saveallbank")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ACCADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> saveAllBankInfo(@RequestBody List<BankInfo> obj, @RequestHeader("Authorization") String jwt)
			throws Exception {
		return bankInfoService.saveAllBankInfo(obj, jwt);
	}

	@GetMapping("/fetchbanklist")
	public List<BankInfo> getBankInfoByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return bankInfoService.getBankInfoByOfficeName(officeName);
	}

	@GetMapping("/fetchbankinfo")
	public BankInfo getBankInfoByAccountNoHandler(@RequestParam Long accountNumber) throws Exception {
		return bankInfoService.getBankInfoByAccountNo(accountNumber);
	}

	@GetMapping("/fetchdataforob")
	public DataForOB getDataForOBForm(@RequestParam String officeName, @RequestParam String bankName,
			@RequestParam String branchName, @RequestParam String accountType) throws Exception {
		return bankInfoService.getDataForOBForm(officeName, bankName, branchName, accountType);
	}

	@PutMapping("/editbank")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ACCADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> editBankInfoHandler(@RequestBody BankInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return bankInfoService.editBankInfo(obj, jwt);
	}

	/* Buyer Firm */
	@Autowired
	private BuyerFirmService buyerFirmService;

	@PostMapping("/savebuyerfirm")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> saveBuyerFirmInfoHandler(@RequestBody BuyerFirmInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return buyerFirmService.saveBuyerFirmInfo(obj, jwt);
	}

	@PutMapping("/editbuyerfirm")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> editBuyerFirmHandler(@RequestBody BuyerFirmInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return buyerFirmService.editBuyerFirm(obj, jwt);
	}

	@GetMapping("/fetchbuyerfirmdata")
	public BuyerFirmInfo getBuyerFirmByFirmNameHandler(@RequestParam String nameOfInstitution) throws Exception {
		return buyerFirmService.getBuyerFirmByFirmName(nameOfInstitution);
	}

	@GetMapping("/fetchbuyerfirmlist")
	public BuyerFirmData getBuyerFirmByOfficeNameHandler(@RequestParam String officeName, String district,
			String bankName) throws Exception {
		return buyerFirmService.getBuyerFirmByOfficeName(officeName, district, bankName);
	}

	@GetMapping("/fetchdataforbuyer")
	public DataForBuyerFirm getDataForBuyerFirmHandler(@RequestParam(required = false) String district,
			@RequestParam(required = false) String block, @RequestParam(required = false) String bankName,
			@RequestParam(required = false) String officeName) throws Exception {
		return buyerFirmService.getDataForBuyerFirm(officeName, block, district, bankName);
	}

	@GetMapping("/fetchbuyername")
	public List<String> getBuyerNameByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return buyerFirmService.getBuyerNameByOfficeName(officeName);
	}

	@GetMapping("/fetchBuyerbyoffice")
	public List<BuyerFirmInfo> getBuyerDataByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return buyerFirmService.getBuyerInfoByOfficeName(officeName);
	}

	@GetMapping("/fetchbuyerdataforbills")
	public DataForBillsReceivablesOb getDataForBillsReceivablesObHandler(@RequestParam String firmType,
			@RequestParam String nameOfInstitution, @RequestParam String officeName) throws Exception {
		return buyerFirmService.getDataForBillsReceivablesOb(firmType, nameOfInstitution, officeName);
	}

	// Contractor info
	@Autowired
	private ContractorService contractorService;

	@PostMapping("/savecontractorinfo")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> saveContractorInfoHandler(@RequestBody ContractorDto obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return contractorService.saveContractorInfo(obj, jwt);
	}

	@PutMapping("/editcontractorinfo")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> editContractorInfoHandler(@RequestBody ContractorInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return contractorService.editContractorInfo(obj, jwt);
	}

	@GetMapping("/fetchcontractoradata")
	public ContractorInfo getContarctorInfoByContractFirmHandler(@RequestParam String officeName, String contractFirm)
			throws Exception {
		return contractorService.getContractorInfoByContractFirm(officeName, contractFirm);
	}

	@GetMapping("/fetchcontractorforextnsn")
	public ContractorDataForExtension getContractorDataForExtension(@RequestParam String officeName,
			String contractFirm) throws Exception {
		ContractorDataForExtension data = new ContractorDataForExtension();
		if (!officeName.isEmpty() && officeName != null) {
			List<String> contractFirmByOfficeName = getContractFirmByOfficeNameHandler(officeName);
			data.setContractorList(contractFirmByOfficeName);
			if (!contractFirm.isEmpty() && contractFirm != null) {
				ContractorInfo contractorInfo = contractorService.getContractorInfoByContractFirm(officeName,
						contractFirm);
				data.setContractThrough(contractorInfo.getContractThrough());
				data.setProprietorName(contractorInfo.getProprietorName());
				data.setDoor(contractorInfo.getDoor());
				data.setStreet(contractorInfo.getStreet());
				data.setDistrict(contractorInfo.getDistrict());
				data.setPincode(contractorInfo.getPincode());
				data.setId(contractorInfo.getId());
			}
		}
		return data;
	}

	@GetMapping("/fetchcontractorbyoffice")
	public List<ContractorInfo> getContarctorInfoByOfficeName(@RequestParam String officeName) throws Exception {
		return contractorService.getContarctorInfoByOfficeName(officeName);
	}

	@GetMapping("/fetchcontractorname")
	public List<String> getContractFirmByOfficeNameHandler(@RequestParam String officeName) throws Exception {
		return contractorService.getContractFirmByOfficeName(officeName);
	}

	@GetMapping("/fetchdataforemdentry")
	public DataForEmdEntry getDataForEmdEntryHandler(@RequestParam String officeName, String contractFirm,
			String gstCategory, String gstRate) throws Exception {
		return contractorService.getDataForEmdEntry(officeName, contractFirm, gstCategory, gstRate);
	}

	@PutMapping("/saveemddata")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> saveEmdDataForContractorHandler(@RequestBody ContractorEmdEntryDto obj)
			throws Exception {
		return contractorService.saveEmdDataForContractor(obj);
	}

	@GetMapping("/fetchdataforrateupdate")
	public DataForRateUpdate getDataForRateUpdateHandler(@RequestParam String officeName, String contractFirm)
			throws Exception {
		return contractorService.getDataForRateUpdate(officeName, contractFirm);
	}

	@PutMapping("/rateupdate/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> saveRateUpdateHandler(@PathVariable Long id, @RequestBody RateUpdateDto obj)
			throws Exception {
		return contractorService.saveRateUpdate(obj, id);
	}

	@GetMapping("/fetchdataforemdrefund")
	public DataForEmdRefund getDataForEmdRefundHandler(@RequestParam String officeName, String contractFirm)
			throws Exception {
		return contractorService.getDataForEmdRefund(officeName, contractFirm);
	}

	@PutMapping("/emdrefund/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN', 'ROLE_FERTUSER', 'ROLE_FERTHOD')")
	public ResponseEntity<String> saveEmdRefundHandler(@PathVariable Long id, @RequestBody ContractorInfo obj)
			throws Exception {
		return contractorService.saveEmdRefund(obj, id);
	}

	@GetMapping("/fetchcontractorstatus")
	public DataForContractorStatus getDataForContractorStatusHandler(@RequestParam String officeName, Integer year)
			throws Exception {
		return contractorService.getDataForContractorStatus(officeName, year);
	}

	@PutMapping("/statusupdate")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> saveStatusHandler(@RequestBody ContractorStatusUpdateDto obj) throws Exception {
		return contractorService.saveContractorStatus(obj);
	}

	@PutMapping("/statusupdate/{id}/{status}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> saveStatusHandler(@PathVariable Long id, @PathVariable String status)
			throws Exception {
		return contractorService.saveContractorStatus(id, status);
	}

	@GetMapping("/fetchcontractorinfo")
	public ContractorInfo getContractFirmByGodownNameHandler(@RequestParam String officeName,
			@RequestParam String godownName) throws Exception {
		return contractorService.getContractFirmByGodownName(officeName, godownName);
	}

	@GetMapping("/fetchdatafordistancemap")
	public DataForDistanceMapping getDataForDistanceMappingHandler(@RequestParam String type,
			@RequestParam String officeName, @RequestParam String godownName, @RequestParam String district,
			@RequestParam String toRegion, @RequestParam String category) throws Exception {
		return contractorService.getDataForDistanceMapping(type, officeName, godownName, district, toRegion, category);
	}

	@PostMapping("/updatedistance/{idNo}")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	public ResponseEntity<String> saveDistanceMapDataHandler(@PathVariable String idNo,
			@RequestBody DistanceMapping obj) throws Exception {
		return contractorService.saveDistanceMapData(obj, idNo);
	}

	// @PutMapping("/updateexistingdistance")
	// @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_FERTADMIN')")
	// public ResponseEntity<String> saveExistingDistanceMapDataHandler(@RequestBody
	// DistanceMapping obj) throws Exception {
	// return contractorService.saveExistingDistanceMapData(obj);
	// }

	@GetMapping("/fetchdistancemappeddata")
	public List<DistanceMappingAbstractTable> getDistanceMappingAbstractTableDataHandler(@RequestParam String region)
			throws Exception {
		return contractorService.getDistanceMappingAbstractTableData(region);
	}

	@Autowired
	private DistanceMappingRepo distanceMappingRepo;

	@GetMapping("/fetchdistancedata")
	public List<DistanceMapping> getDistanceData(@RequestParam String officeName, @RequestParam String godownName) {
		return distanceMappingRepo.findByOfficeNameAndGodownName(officeName, godownName);
	}

}
