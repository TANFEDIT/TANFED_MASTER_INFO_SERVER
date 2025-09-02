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
public class OfficeInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String officeType;

	@Column
	private String officeName;
	
	@Column
	private String officeCode;

	@Column
	private String manager;

	@Column
	private String organization;

	@Column
	private String door;

	@Column
	private String street;

	@Column
	private String district;

	@Column
	private Integer pincode;

	@Column
	private String contactName;

	@Column
	private String contactNo1;

	@Column
	private String contactNo2;

	@Column
	private String email;

	@Column
	private String areaOperation;

	@Column
	private String isUnitOffice;

	@Column
	private String unitOfficeType;

	@Column
	private List<String> productionUnit;

	@Column
	private List<String> serviceUnit;

	@Column
	private List<String> districtList;
	
	@Column
	private List<String> empId;
}
