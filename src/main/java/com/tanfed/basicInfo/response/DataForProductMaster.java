package com.tanfed.basicInfo.response;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForProductMaster {

	private List<String> supplierList;
	private String supplierGst;
	private Set<String> productCategoryList;
	private Set<String> productGroupList;
	private Set<String> gstCategoryList;
	private List<Double> gstRateList;
	private GstRateData gstData;
}
