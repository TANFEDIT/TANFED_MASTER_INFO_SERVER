package com.tanfed.basicInfo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanfed.basicInfo.entity.ContractorInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ReassignedGodown {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private List<String> additionalGodown;
	
	@Column
	private String rateDefinedAs;
	
	@Column
	private String contractorName;

	@ManyToOne
	@JoinColumn(name = "contractor_id")
	@JsonIgnore
	private ContractorInfo contractor;
}
