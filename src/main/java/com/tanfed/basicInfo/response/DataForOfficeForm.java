package com.tanfed.basicInfo.response;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForOfficeForm {

	private List<String> officeList;
	private Set<String> districtList;
}
