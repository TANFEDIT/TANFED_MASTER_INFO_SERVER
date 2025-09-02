package com.tanfed.basicInfo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DepartmentInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private List<String> empId;
	
	@Column
	private String officeName;
	
	@Column
	private String officeCode;
	
	@Column
	private String door;
	
	@Column
	private String street;
	
	@Column
	private Integer pincode;
	
	@Column
	private String district;
	
	@Column
	private String authorizedPerson;
	
	@Column
	private String contact1;
	
	@Column
	private String contact2;
	
	@Column
	private String email;
	
	@Column
	private String website;
	
	@Column 
	private String department;

}
