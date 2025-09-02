package com.tanfed.basicInfo.response;

import java.time.LocalDate;
import java.util.List;

import com.tanfed.basicInfo.entity.ContractorTenderData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForEmdRefund {

	public List<String> officeList;
	public List<String> contractorList;
	private Long dataId;
	private String gstNo;
	private String contractThrough;
	private List<ContractorTenderData> tenderData;
	private Double emdAmount;
	private Double solvencyValue;
	private LocalDate emdReceivedOn;
	private Double additionalEmd;
	
}
