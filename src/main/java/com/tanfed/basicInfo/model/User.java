package com.tanfed.basicInfo.model;

import java.time.LocalDate;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

	private Long id;
	
	private String empId;
	
	private String empName;
	
	private String designation;
	
	private LocalDate dob;
	
	private LocalDate doj;
	
	private LocalDate joiningDate;
	
	private LocalDate dor;
	
	private String officeName;
	
	private String department;
	
	private String rcNo;
	
	private LocalDate date;
	
	private Long mobileNo1; 
	
	private Long mobileNo2;
	
	private String emailId;
	
	private Long aadharNo;
	
	private String panNo;
	
	private String password;
	
	private List<String> role;
	
	private String currentAddress;
	
	private String permanentAddress;
	
	private String state;

	private Boolean sameAsChecked;
	
	private String imgName;
	
	private String imgType;
	
	private byte[] imgData;
	
	
}
