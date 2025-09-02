package com.tanfed.basicInfo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContractorDataForExtension {

	private List<String> contractorList;
	private String contractThrough;
	private String proprietorName;
	private String door;
	private String street;
	private String district;
	private Integer pincode;
	private Long id;
}
