package com.tanfed.basicInfo.response;

import java.util.List;
import java.util.Set;

import com.tanfed.basicInfo.entity.BuyerFirmInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerFirmData {

	private List<String> officeList;
	private Set<String> districtList;
	private List<BuyerFirmInfo> buyerData;
}
