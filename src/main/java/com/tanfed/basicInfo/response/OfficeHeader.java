package com.tanfed.basicInfo.response;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeHeader {

	private String empId;
	private List<String> role;
	private String name;
	private String designation;
	
	
	private String tanNo;
	private String officeName;
	private String officeType;
	private String door;
	private String street;
	private String district;
	private Integer pincode;
	private String gst;
	private String imgName;
	private String imgType;
	private byte[] imgData;
	
}
