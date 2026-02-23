package com.tanfed.basicInfo.entity;

import java.util.List;

import com.tanfed.basicInfo.utils.ListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class GodownInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String officeName;

	@Column
	private String officeCode;

	@Column
	private String district;

	@Column
	private String taluk;

	@Column
	private String block;

	@Column
	private String village;

	@Column
	private String ifmsId;

	@Column
	private String godownType;

	@Column
	private String godownName;

	@Column
	private String door;

	@Column
	private String street;

	@Column
	private Integer pincode;

	@Column
	private String totalCapacity;

	@Column
	private Long numberOfGodowns;

	@Convert(converter = ListConverter.class)
	private List<String> capacities;

	@Column
	private String keeperName;

	@Column
	private String contactNo1;

	@Column
	private String contactNo2;

	@Column
	private String gkDesignation;

	@Convert(converter = ListConverter.class)
	private List<String> empId;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<LicenseData> license;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "godown")
	private List<GodownInsuranceData> insurance;

}
