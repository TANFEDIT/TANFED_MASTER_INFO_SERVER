package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.ProductMaster;
import com.tanfed.basicInfo.response.DataForProductMaster;

public interface ProductMasterService {

	public ResponseEntity<String> saveProduct(List<ProductMaster> obj, String jwt) throws Exception;

	public ResponseEntity<String> editProduct(ProductMaster obj, String jwt) throws Exception;
	
	public List<String> getProductName() throws Exception;
	
	public List<ProductMaster> getProductData() throws Exception;
	
	public ProductMaster getProductDataByProductName(String productName) throws Exception;
	
	public DataForProductMaster getDataForProductMaster(String formType,String activity, String productCategory, String supplierName, 
			String gstCategory, Double gstRate, String productName) throws Exception;
}
