package com.tanfed.basicInfo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorStatusUpdateDto {

	private List<String> godownAddedList;
	private String contractFirm;
	private String officeName;
	private String rateDefinedAs;
	private String blockedContractor;
}
