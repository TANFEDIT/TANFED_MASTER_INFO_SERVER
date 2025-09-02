package com.tanfed.basicInfo.model;

import java.time.LocalDate;
import java.util.List;

import com.tanfed.basicInfo.entity.ContractorGstData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorEmdEntryDto {

	private Long id;
	private Double emdAmount;
	private Double solvencyValue;
	private LocalDate emdReceivedOn;
	private Double additionalEmd;
	private String gstNo;
	private String gstReturnType;
	
	private List<String> godownAddedList;
	private List<ContractorGstData> gstAddedList;
	
	private LocalDate rateFrom;
	private LocalDate rateTo;

	private Double wagonClearance;
	private Double loadingCharges;
	private Double unloadingCharges;
	private Double hillRate;

	private Double zero_seven;
	private Double eight_twenty;
	private Double twentyone_fifty;
	private Double fiftyone_seventyfive;
	private Double seventysix_hundred;
	private Double hundredone_onetwentyfive;
	private Double onetwosix_onefifty;
	private Double onefiftyone_oneseventyfive;
	private Double oneseventysix_twohundred;
	private Double abovetwohundredone;
}
