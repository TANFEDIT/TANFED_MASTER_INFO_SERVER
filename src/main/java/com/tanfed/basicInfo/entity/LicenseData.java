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
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LicenseData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Convert(converter = ListConverter.class)
	private List<String> empId;
	
	@Column
	private String officeName;
	
	@Column
	private String filename;
	
	@Column
	private String filetype;
	
	@Lob
	@Column(length = 1000000)
	private byte[] filedata;
	
	@Column
	private String licenseType;
	
	@Column
	private String licenseFor;
	
	@Column
	private String licenseNumber;
	
	@Column
	private LocalDate validFrom;
	
	@Column
	private LocalDate validTo;

}
