package com.tanfed.basicInfo.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForGodownInfo {

	private DistrictData districtData;
	private List<String> licenseNoList;
	private LocalDate fromDate;
	private LocalDate toDate;
}
