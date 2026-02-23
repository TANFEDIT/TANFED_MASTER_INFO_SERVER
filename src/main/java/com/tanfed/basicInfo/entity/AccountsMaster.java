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
public class AccountsMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String mainGroup;

	@Column
	private String mainHead;

	@Column
	private String subHead;

	@Column
	private String groupCode;

	@Column
	private String mainHeadCode;

	@Column
	private String subHeadCode;

	@Column
	private String associatedWith;

	@Column
	private String accGroup;

	@Column
	private String category;

	@Column
	private String categoryCode;

	@Column
	private LocalDate date = LocalDate.now();

	@Convert(converter = ListConverter.class)
	private List<String> empId;
}
