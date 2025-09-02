package com.tanfed.basicInfo.response;

import java.util.List;

import com.tanfed.basicInfo.entity.ContractorChargesData;
import com.tanfed.basicInfo.entity.ContractorTenderData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForRateUpdate {

	private List<String> officeList;
	private List<String> contractorList;
	private Long dataId;
	private String gstNo;
	private String contractThrough;
	private List<ContractorTenderData> tenderData;
	private List<ContractorChargesData> chargesData;
}
