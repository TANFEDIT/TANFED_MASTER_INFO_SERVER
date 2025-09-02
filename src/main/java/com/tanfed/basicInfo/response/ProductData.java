package com.tanfed.basicInfo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {

	private String productGroup;
	private String productCategory;
	private String supplier;
	private String supplierGst;
	private String standardUnits;
	private String packing;
	private String hsncode;
}
