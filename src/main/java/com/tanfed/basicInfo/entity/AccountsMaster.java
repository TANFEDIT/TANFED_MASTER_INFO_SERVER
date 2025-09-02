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
	private String AccGroup;
	
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
	private LocalDate date = LocalDate.now();
	
	@Column
	private List<String> empId; 
}
