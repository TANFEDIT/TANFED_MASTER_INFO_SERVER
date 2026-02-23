package com.tanfed.basicInfo.entity;

import java.time.LocalDate;
import java.util.List;

import com.tanfed.basicInfo.response.GstRateData;
import com.tanfed.basicInfo.utils.ListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
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
public class ProductMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Convert(converter = ListConverter.class)
	private List<String> empId;
	
	@Column
	private String activity;
	
	@Column(unique = true)
	private String brandName;
	
	@Column
	private String hsnCode;
	
	@Column
	private String packing;
	
	@Column
	private String productType;
	
	@Column
	private String productCategory;
	
	@Column
	private String productGroup;
	
	@Column(unique = true)
	private String productName;
	
	@Column
	private String productSupply;
	
	@Column
	private String standardUnits;
	
	@Column
	private String supplierName;
	
	@Column
	private String supplierGst;
	
	@Column
	private String usedAs;
	
	@Column
	private String batchNo;
	
	@Column
	private String certification;
	
	@Column
	private LocalDate date = LocalDate.now();
	
	@Column
	private String gstCategory;
	private String caseContains;
	
	@Column
	private Double gstRate;
	
	@Embedded
	private GstRateData gstData;
}
