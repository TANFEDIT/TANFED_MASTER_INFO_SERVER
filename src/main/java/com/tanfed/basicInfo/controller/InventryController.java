package com.tanfed.basicInfo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanfed.basicInfo.entity.ProductConfig;
import com.tanfed.basicInfo.entity.ProductMaster;
import com.tanfed.basicInfo.entity.Terms_Price_Config;
import com.tanfed.basicInfo.response.DataForProductMaster;
import com.tanfed.basicInfo.response.InventryFilterData;
import com.tanfed.basicInfo.service.ProductConfigService;
import com.tanfed.basicInfo.service.ProductMasterService;
import com.tanfed.basicInfo.service.Terms_Price_Config_Service;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/inventrymaster")
public class InventryController {

	// product config
	@Autowired
	private ProductConfigService productConfigService;

	@PostMapping("/saveproductconfig")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> saveProductConfigHandler(@RequestBody List<ProductConfig> obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return productConfigService.saveProductConfig(obj, jwt);
	}

	@PutMapping("/editproductconfig")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> editProductConfigHandler(@RequestBody ProductConfig obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return productConfigService.editProductConfig(obj, jwt);
	}

	@GetMapping("/fetchproductconfiglist")
	public List<ProductConfig> getProductConfigListHandler() throws Exception {
		return productConfigService.getProductConfigList();
	}

	@GetMapping("/fetchproductcategory")
	public List<String> getProductCategoryByActivityHandler(@RequestParam String activity) throws Exception {
		return productConfigService.getProductCategoryByActivity(activity);
	}

	// Terms_Price_Config
	@Autowired
	private Terms_Price_Config_Service terms_Price_Config_Service;

	@PostMapping("/savetpconfig")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> saveTerms_Price_ConfigHandler(@RequestBody Terms_Price_Config obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return terms_Price_Config_Service.saveTerms_Price_Config(obj, jwt);
	}

	@PutMapping("/edittpconfig")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> editTerms_Price_ConfigHandler(@RequestBody Terms_Price_Config obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return terms_Price_Config_Service.editTerms_Price_Config(obj, jwt);
	}

	@GetMapping("/fetchtpconfiglist")
	public List<Terms_Price_Config> getTerms_Price_ConfigListHandler() throws Exception {
		return terms_Price_Config_Service.getTerms_Price_ConfigList();
	}

	@GetMapping("/fetchsupplymode")
	public List<String> getSupplyModeHandler(@RequestParam String activity) throws Exception {
		return terms_Price_Config_Service.getSupplyMode(activity);
	}

	@GetMapping("/fetchpaymentmode")
	public List<String> gePaymentModeHandler(@RequestParam String activity) throws Exception {
		return terms_Price_Config_Service.gePaymentMode(activity);
	}

	@GetMapping("/fetchheadname")
	public List<String> getHeadNameByActivityHandler(@RequestParam String activity) throws Exception {
		return terms_Price_Config_Service.getHeadNameByActivity(activity);
	}

	// product master
	@Autowired
	private ProductMasterService productMasterService;

	@PostMapping("/saveproductmaster")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> saveProductHandler(@RequestBody List<ProductMaster> obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return productMasterService.saveProduct(obj, jwt);
	}

	@PutMapping("/editproductmaster")
	@PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_MARKADMIN', 'ROLE_FERTADMIN', 'ROLE_SPAIADMIN')")
	public ResponseEntity<String> editProductHandler(@RequestBody ProductMaster obj,
			@RequestHeader("Authorization") String jwt) throws Exception {
		return productMasterService.editProduct(obj, jwt);
	}

	@GetMapping("/fetchproduct")
	public ProductMaster getProductDataByProductNameHandler(@RequestParam String productName) throws Exception {
		return productMasterService.getProductDataByProductName(productName);
	}

	@GetMapping("/fetchproductName")
	public List<String> getProductName() throws Exception {
		return productMasterService.getProductName();
	}

	@GetMapping("/fetchproductdata")
	public List<ProductMaster> getProductDataHandler() throws Exception {
		return productMasterService.getProductData();
	}

	@GetMapping("/fetchdataforproductmaster/{formType}")
	public DataForProductMaster getDataForProductMasterHandler(@PathVariable String formType,
			@RequestParam String activity, String productCategory, String supplierName, String gstCategory,
			Double gstRate, String productName) throws Exception {
		return productMasterService.getDataForProductMaster(formType, activity, productCategory, supplierName,
				gstCategory, gstRate, productName);
	}

	@GetMapping("/fetchmasterfilterdata/{formType}")
	public InventryFilterData getInventryFilterDataHandler(@PathVariable String formType) throws Exception {
		InventryFilterData data = new InventryFilterData();
		switch (formType) {
		case "productConfig": {
			data.setProductConfig(productConfigService.getProductConfigList());
			return data;
		}
		case "termsPriceConfig": {
			data.setTermsPriceConfig(terms_Price_Config_Service.getTerms_Price_ConfigList());
			return data;
		}
		case "productMaster": {
			data.setProductMaster(productMasterService.getProductData());
			return data;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + formType);
		}
	}

}
