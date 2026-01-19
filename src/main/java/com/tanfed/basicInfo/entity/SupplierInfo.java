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
public class SupplierInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Column
//	private List<String> empId;
	
	private String contact1;
	
	private String contact2;
	
	private String door;
	
	private String street;
	
	private Integer pincode;
	
	private String district;
	
	private String email;
	
	private String supplierName;
	
	private String supplierPanNo;
	
	private String supplierTanNo;
	
	private String supplierTin;
	
	private String supplierGst;
	
	private String relationship;
	
	private List<String> supplierOf;
	
	private String website;
	
	
	
	
}
