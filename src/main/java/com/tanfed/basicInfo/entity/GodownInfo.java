package com.tanfed.basicInfo.entity;

import java.time.LocalDate;
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
public class GodownInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String officeName;
	
	@Column
	private String officeCode;
	
	
	
	
	@Column
	private String district;

	@Column
	private String taluk;

	@Column
	private String block;

	@Column
	private String village;
	
	
	

	@Column
	private String ifmsId;

	@Column
	private String godownType;

	@Column
	private String godownName;

	
	
	
	@Column
	private String door;

	@Column
	private String street;

	@Column
	private Integer pincode;

	
	
	@Column
	private String licenseNo;

	@Column
	private LocalDate insuranceFrom;

	@Column
	private LocalDate insuranceTo;

	@Column
	private String totalCapacity;

	@Column
	private Long numberOfGodowns;

	@Column
	private List<String> capacities;

	@Column
	private String keeperName;

	@Column
	private String contactNo1;

	@Column
	private String contactNo2;

	@Column
	private String gkDesignation;

	@Column
	private String hoLetterRcNo;

	@Column
	private LocalDate insuranceDate;

	@Column
	private LocalDate validityFrom;

	@Column
	private LocalDate validityTo;
	
	@Column
	private List<String> empId;
	

}
