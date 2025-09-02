package com.tanfed.basicInfo.entity;

import java.time.LocalDate;

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
public class ContractorTenderData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String contractApproval;
	
	@Column
	private String hoIrRcno;

	@Column
	private String approvalPeriod;

	@Column
	private LocalDate validityFrom;

	@Column
	private LocalDate validityTo;

	@Column
	private LocalDate hoLetterDate;
}
