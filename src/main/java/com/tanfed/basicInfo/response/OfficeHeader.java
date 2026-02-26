package com.tanfed.basicInfo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeHeader {

	private String gst;
	private String imgName;
	private String imgType;
	private byte[] imgData;
	
}
