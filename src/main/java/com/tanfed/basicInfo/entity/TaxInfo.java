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

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TaxInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String gstNo;
	
	@Column(nullable = false)
	private String panNo;
	
	@Column(nullable = false)
	private String tinNo;
	
	@Column(nullable = false)
	private String TanNo;
	
	@Column
	private String gstCategory;
	
	@Column
	private Double gstRate;
	
	@Column
	private Double cgstRate;
	
	@Column
	private Double sgstRate;
	
	@Column
	private Double igstRate;

	@Column
	private Double rcmRate;
	
	@Column
	private LocalDate date = LocalDate.now();
	
	@Column
	private List<String> empId; 
}
