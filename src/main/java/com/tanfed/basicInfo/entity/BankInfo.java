package com.tanfed.basicInfo.entity;

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
public class BankInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String officeName;
	
	private Long accountNumber;
	
	private String accountType;
	
	private String bankGST;
	
	private String bankId;
	
	private String bankName;
	
	private String branchName;
	
	private String door;
	
	private String street;
	
	private String district;
	
	private Long pincode;	
	
	private String contact1;
	
	private String contact2;
	
	private String email;
	
	private String website;
	
	private String bankType;
	
	private String ifsc;
	
	private String micr;
	
	private String cif;
	
	private List<String> empId;

}
