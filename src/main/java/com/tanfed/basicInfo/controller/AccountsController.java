package com.tanfed.basicInfo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanfed.basicInfo.entity.*;
import com.tanfed.basicInfo.model.AccountsHead;
import com.tanfed.basicInfo.response.GstRateData;
import com.tanfed.basicInfo.service.*;

@RestController
@RequestMapping("/api/accountsmaster")
public class AccountsController {

	@Autowired
	private AccountsMasterService accountsMasterService;

	// Accounts master mapping
	@PostMapping("/saveaccountsmaster")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ACCADMIN')")
	public ResponseEntity<String> saveAccountsMasterHandler(@RequestBody List<AccountsMaster> obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return accountsMasterService.saveAccountsMaster(obj, jwt);
	}

	@PutMapping("/editaccountsmaster")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ACCADMIN')")
	public ResponseEntity<String> editAccountsMasterHandler(@RequestBody AccountsMaster obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return accountsMasterService.editAccountsMaster(obj, jwt);
	}

	@GetMapping("/fetchaccountsmasterlist")
	public List<AccountsMaster> accountsMasterListHandler() throws Exception {
		return accountsMasterService.accountsMasterList();
	}

	@GetMapping("/fetchmainhead")
	public List<String> getMainHeadHandler() throws Exception {
		return accountsMasterService.getMainHead();
	}

	@GetMapping("/fetchacchead")
	public AccountsHead getSubHeadByMainHeadHandler(@RequestParam String mainHead,
			@RequestHeader("Authorization") String jwt) throws Exception {
		AccountsHead data = new AccountsHead();
		data.setMainHeadList(new HashSet<String>(accountsMasterService.getMainHead()));
		if (!mainHead.isEmpty() && mainHead != null) {
			data.setSubHeadList(accountsMasterService.getSubHeadByMainHead(mainHead, jwt));
		}
		return data;
	}

	// tax info
	@Autowired
	private TaxInfoService taxInfoService;

	@PostMapping("/savetaxinfo")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ACCADMIN')")
	public ResponseEntity<String> saveTaxInfoHandler(@RequestBody List<TaxInfo> obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return taxInfoService.saveTaxInfo(obj, jwt);
	}

	@PutMapping("/edittaxinfo")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ACCADMIN')")
	public ResponseEntity<String> editTaxInfoHandler(@RequestBody TaxInfo obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return taxInfoService.editTaxInfo(obj, jwt);
	}

	@GetMapping("/fetchtaxinfolist")
	public List<TaxInfo> findTaxInfoListHandler() throws Exception {
		return taxInfoService.findTaxInfoList();
	}

	@GetMapping("/fetchgstcategory")
	public Set<String> findCategoryListHandler() throws Exception {
		return taxInfoService.findCategoryList();
	}

	@GetMapping("/fetchgstrate")
	public List<Double> findGstRateByCategoryHandler(@RequestParam String gstCategory) throws Exception {
		return taxInfoService.findGstRateByCategory(gstCategory);
	}

	@GetMapping("/fetchgstdata")
	public GstRateData findGstDataByGstRateHandler(@RequestParam String gstCategory, Double gstRate) throws Exception {
		return taxInfoService.findGstDataByGstRate(gstCategory, gstRate);
	}

	@GetMapping("/datavalidate")
	public String validateCategoryAndRateHandler(@RequestParam String category, Double rate) throws Exception {
		return taxInfoService.validateCategoryAndRate(category, rate);
	}

	// Beneficiary Master
	@Autowired
	private BeneficiaryMasterService beneficiaryMasterService;

	@PostMapping("/savebeneficiarymaster")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> saveBeneficiaryMaster(@RequestBody List<BeneficiaryMaster> obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return beneficiaryMasterService.saveBeneficiaryMaster(obj, jwt);
	}

	@PutMapping("/editbeneficiarymaster")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ROADMIN')")
	public ResponseEntity<String> editBeneficiaryMaster(@RequestBody BeneficiaryMaster obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return beneficiaryMasterService.editBeneficiaryMaster(obj, jwt);
	}

	@GetMapping("/fetchbeneficiarymasterdata")
	public List<BeneficiaryMaster> getBeneficiaryMasterByName(@RequestParam String beneficiaryName,
			@RequestParam String officeName) throws Exception {
		return beneficiaryMasterService.getBeneficiaryMasterByName(beneficiaryName, officeName);
	}

	@GetMapping("/fetchbeneficiarymasterlist")
	public List<BeneficiaryMaster> getBeneficiaryListByOfficeName() throws Exception {
		return beneficiaryMasterService.getBeneficiaryListByOfficeName();
	}

//	@GetMapping("/fetchbeneficiaryname")
//	public List<String> getBeneficiaryNameListByOfficeName(@RequestParam String officeName) throws Exception {
//		return beneficiaryMasterService.getBeneficiaryNameListByOfficeName(officeName);
//	}

	@GetMapping("/fetchdataforbeneficiarymaster")
	public List<String> fetchDataForBeneficiaryMasterHandler(@RequestParam String officeName) throws Exception {
		return beneficiaryMasterService.fetchDataForBeneficiaryMaster(officeName);
	}

}
