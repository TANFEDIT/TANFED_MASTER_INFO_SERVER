package com.tanfed.basicInfo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tanfed.basicInfo.entity.BuyerFirmInfo;
import com.tanfed.basicInfo.response.BuyerFirmData;
import com.tanfed.basicInfo.response.DataForBillsReceivablesOb;
import com.tanfed.basicInfo.response.DataForBuyerFirm;

public interface BuyerFirmService {

	public ResponseEntity<String> saveBuyerFirmInfo(BuyerFirmInfo obj, String jwt) throws Exception;
	
	public BuyerFirmInfo getBuyerFirmByFirmName(String ifmsId) throws Exception;
	
	public BuyerFirmData getBuyerFirmByOfficeName(String officeName, String district, String bankName) throws Exception;
	
	public ResponseEntity<String> editBuyerFirm(BuyerFirmInfo obj, String jwt) throws Exception;
	
	public List<String> getBuyerIfmsIdByOfficeName(String officeName) throws Exception;
	
	public List<BuyerFirmInfo> getBuyerInfoByOfficeName(String officeName) throws Exception;

	public DataForBuyerFirm getDataForBuyerFirm(String officeName, String block, String district, String bankName)
			throws Exception;

	public DataForBillsReceivablesOb getDataForBillsReceivablesOb(String firmType, String ifmsId, String officeName)throws Exception;

}
