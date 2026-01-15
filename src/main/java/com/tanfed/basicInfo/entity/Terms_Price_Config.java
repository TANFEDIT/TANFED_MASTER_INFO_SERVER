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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Terms_Price_Config {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private LocalDate date = LocalDate.now();
	
	@Column
	private String supplyType;
	
	@Column
	private String headName;
	
	@Column
	private String supplyMode;
	
	@Column
	private String activity;
	
	@Column
	private String paymentMode;
	
	@Column
	private String salesCreditPeriod;
	
	@Column
	private String purchaseCreditPeriod;
	
	@Convert(converter = ListConverter.class)
	private List<String> empId;
}
