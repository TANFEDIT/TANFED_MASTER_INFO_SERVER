package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.SupplierInfo;
import com.tanfed.basicInfo.response.DataForBillsPayableOb;

public interface SupplierInfoService {

	public ResponseEntity<String> saveSupplierInfo(SupplierInfo obj, String jwt) throws Exception;
	
	public SupplierInfo getSupplierInfoBySupplierName(String supplierName) throws Exception;
	
	public List<SupplierInfo> getSupplierInfo() throws Exception;
	
	public ResponseEntity<String> editSupplierInfo(SupplierInfo obj, String jwt) throws Exception;
	
	public List<String> getSupplierName(String activity) throws Exception;

	public DataForBillsPayableOb getDataForBillsPayableOb(String activity, String supplierName) throws Exception;

	public List<SupplierInfo> getSupplierInfo(String activity);
	
}
