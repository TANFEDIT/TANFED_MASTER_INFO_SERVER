package com.tanfed.basicInfo.response;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForOB {

	private Set<String> bankList;
	private Set<String> BranchNameList;
	private Set<String> accTypeList;
	private List<Long> accNoList;
}
