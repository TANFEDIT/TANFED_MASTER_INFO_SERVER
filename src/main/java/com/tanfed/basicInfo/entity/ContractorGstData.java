package com.tanfed.basicInfo.entity;

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
public class ContractorGstData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String gstRateFor;

	@Column
	private String gstCategory;

	@Column
	private Double gstRate;

	@Column
	private Double sgstRate;

	@Column
	private Double cgstRate;

	@Column
	private Double igstRate;
	
}
