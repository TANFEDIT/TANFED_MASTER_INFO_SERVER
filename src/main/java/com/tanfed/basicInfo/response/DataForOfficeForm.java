package com.tanfed.basicInfo.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataForOfficeForm {

	private List<String> officeList;
	private List<String> districtList;
}
