package com.tanfed.basicInfo.response;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictData {

	private List<String> districtList;
	private List<String> talukList;
	private Set<String> blockList;
	private List<String> villageList;
	
}
