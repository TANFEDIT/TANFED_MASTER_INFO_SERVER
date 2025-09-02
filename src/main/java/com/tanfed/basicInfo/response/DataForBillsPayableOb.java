package com.tanfed.basicInfo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForBillsPayableOb {

	private List<String> supplierNameList;
	private String supplierGst;
	private String street;
	private Integer pincode;
	private String district;
}
