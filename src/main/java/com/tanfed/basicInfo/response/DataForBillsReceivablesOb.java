package com.tanfed.basicInfo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForBillsReceivablesOb {

	private List<String> ifmsIdList;
	private String nameOfInstitution;
	private String address;
	private String district;
	private String buyerGstNo;
}
