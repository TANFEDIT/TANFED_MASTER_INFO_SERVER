package com.tanfed.basicInfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanfed.basicInfo.entity.DistanceMapping;

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
public class DistanceMapTableData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String ifmsId;
	
	@Column
	private String name;
	
	@Column
	private Double km;
	
	@Column
	private Double hillKm;
	
	@Column
	private String status;

	@ManyToOne
	@JoinColumn(name = "dist_mapping")
	@JsonIgnore
	private DistanceMapping dist_mapping;
}
