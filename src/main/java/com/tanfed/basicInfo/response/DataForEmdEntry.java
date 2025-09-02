package com.tanfed.basicInfo.response;

import java.util.List;
import java.util.Set;

import com.tanfed.basicInfo.entity.ContractorTenderData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForEmdEntry {

	private List<String> officeList;
	private List<String> contractorList;
	private Set<String> gstCategoryList;
	private List<Double> gstRateList;
	private GstRateData gstData;
	private List<String> godownNameList;
	
	private String contractThrough;
	private List<ContractorTenderData> tenderData;
	private Long id;
}
