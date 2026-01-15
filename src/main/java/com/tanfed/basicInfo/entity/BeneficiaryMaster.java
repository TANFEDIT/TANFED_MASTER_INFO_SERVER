package com.tanfed.basicInfo.entity;

import java.time.LocalDate;
import java.util.List;

import com.tanfed.basicInfo.utils.ListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class BeneficiaryMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String officeName;
	
	@Column
	private String beneficiaryName;

	@Column
	private String gstNo;
	
	@Column
	private String panNo;
	
	@Column
	private String bankName;
	
	@Column
	private Long accountNo;
	
	@Column
	private String accountType;
	
	@Column
	private String ifscCode;
	
	@Convert(converter = ListConverter.class)
	private List<String> beneficiaryApplicableToHoAccount;
	
	@Convert(converter = ListConverter.class)
	private List<String> empId;
	
	@Column
	private LocalDate date = LocalDate.now();
}
