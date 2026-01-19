package com.tanfed.basicInfo.entity;

import java.time.LocalDate;
import java.util.List;

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
public class BuyerFirmInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	private List<String> empId;
	
	private String officeName;
	
	private String district;
	
	private String taluk;
	
	private String block;
	
	private String village;
	
	private String firmRegister;
	
	private String supplyTo;
	
	private String firmType;
	
	private String buyerGstNo;
	
	private List<String> businessWithTanfed;
	
	
	
	private String LicenceNo;
	
	private LocalDate ValidityDateFrom;
	
	private LocalDate ValidityDateTo;
	
	
	
	private String ifmsIdNo;
	
	private String nameOfInstitution;
	
	private String address;
	
	private String emailId;
	
	private String contact1;
	
	private String contact2;
	
	
	
	private String bankOperation;
	
	

	private String bankName;
	
	private String branchName;
	

}
