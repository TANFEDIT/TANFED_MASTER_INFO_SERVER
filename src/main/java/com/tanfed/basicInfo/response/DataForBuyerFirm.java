package com.tanfed.basicInfo.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForBuyerFirm {

	private Set<String> bankNameList;
	private Set<String> branchNameList;
	private DistrictData districtData;
}
