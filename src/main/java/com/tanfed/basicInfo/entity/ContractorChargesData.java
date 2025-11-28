package com.tanfed.basicInfo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ContractorChargesData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private LocalDate rateFrom;
	
	@Column
	private LocalDate rateTo;
	
	@Column
	private LocalDate updateDate;
	
	
	
	
	@Column
	private Double wagonClearance;
	
	@Column
	private Double loadingCharges;
	
	@Column
	private Double unloadingCharges;
	
	@Column
	private Double hillRate;
	
	
	
	
	@Column
	private Double zero_seven;
	
	@Column
	private Double eight_twenty;
	
	@Column
	private Double twentyone_fifty;
	
	@Column
	private Double fiftyone_seventyfive;
	
	@Column
	private Double seventysix_hundred;
	
	@Column
	private Double hundredone_onetwentyfive;
	
	@Column
	private Double onetwosix_onefifty;
	
	@Column
	private Double onefiftyone_oneseventyfive;
	
	@Column
	private Double oneseventysix_twohundred;
	
	@Column
	private Double abovetwohundredone;
	

	@ManyToOne
	@JoinColumn(name = "contractor_id", nullable = false)
	@JsonIgnore
	private ContractorInfo contractor;
}
