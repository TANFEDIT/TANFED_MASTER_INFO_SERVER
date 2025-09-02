package com.tanfed.basicInfo.response;

import java.util.List;

import com.tanfed.basicInfo.model.ContractorStatusTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForContractorStatus {

	private List<String> officeList;
	private List<ContractorStatusTable> tableData;
	private List<String> contractorList;
	private List<String> inactiveContractorList;
	private List<String> godownList;
	
}
