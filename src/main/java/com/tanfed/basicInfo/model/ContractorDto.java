package com.tanfed.basicInfo.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorDto {

	private Long id;
	private String officeName;
	private String officeCode;
	private String contractApproval;
	private String contractThrough;
	private String contractFirm;
	private String proprietorName;	

	
	private String door;
	private String street;
	private String district;
	private Integer pincode;
	
	
	
	private String hoIrRcno;
	private String approvalPeriod;
	private LocalDate validityFrom;
	private LocalDate validityTo;
	private LocalDate hoLetterDate;
	
	
}
