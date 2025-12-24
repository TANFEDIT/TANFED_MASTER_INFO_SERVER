package com.tanfed.basicInfo.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.TaxInfo;
import com.tanfed.basicInfo.response.GstRateData;

public interface TaxInfoService {

	public ResponseEntity<String> saveTaxInfo(List<TaxInfo> obj, String jwt) throws Exception;
	
	public List<TaxInfo> findTaxInfoList() throws Exception;
	
	public ResponseEntity<String> editTaxInfo(TaxInfo obj, String jwt) throws Exception;
	
	public Set<String> findCategoryList() throws Exception;
	
	public String validateCategoryAndRate(String category, Double rate) throws Exception;
	
	public List<Double> findGstRateByCategory(String gstCategory) throws Exception;
	
	public GstRateData findGstDataByGstRate(String gstCategory, Double gstRate) throws Exception;
}
