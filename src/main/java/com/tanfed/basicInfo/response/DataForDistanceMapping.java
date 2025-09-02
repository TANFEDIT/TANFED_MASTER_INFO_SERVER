package com.tanfed.basicInfo.response;

import java.util.List;

import com.tanfed.basicInfo.model.DistanceMapTableData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForDistanceMapping {

	private List<String> officeList;
	private List<String> godownList;
	
	private List<DistanceMapTableData> tableData;
	private List<String> districtList;
	private Long idNo;
}
