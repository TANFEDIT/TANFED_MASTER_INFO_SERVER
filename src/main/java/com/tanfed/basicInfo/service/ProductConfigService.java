package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.ProductConfig;

public interface ProductConfigService {

	public ResponseEntity<String> saveProductConfig(List<ProductConfig> obj, String jwt) throws Exception;

	public ResponseEntity<String> editProductConfig(ProductConfig obj, String jwt) throws Exception;
	
	public List<ProductConfig> getProductConfigList() throws Exception;
	
	public List<String> getProductCategoryByActivity(String activity) throws Exception;

}
