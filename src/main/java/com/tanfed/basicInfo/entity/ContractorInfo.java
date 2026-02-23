package com.tanfed.basicInfo.entity;

import java.time.LocalDate;
import java.util.List;

import com.tanfed.basicInfo.model.ReassignedGodown;
import com.tanfed.basicInfo.utils.ListConverter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class ContractorInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Convert(converter = ListConverter.class)
	private List<String> empId;

//	Contractor create
	@Column
	private String officeName;

	@Column
	private String officeCode;

	@Column
	private String contractThrough;

	@Column
	private String contractFirm;

	@Column
	private String proprietorName;

	@Column
	private String door;

	@Column
	private String street;

	@Column
	private String district;

	private String contactNo1;
	private String contactNo2;
	private String email;

	@Column
	private Integer pincode;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "contractor")
	private List<ContractorTenderData> tenderData;

//	EMD Entry
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contractor")
	private List<ContractorGstData> gstData;

	@Column
	private Double emdAmount;

	@Column
	private Double solvencyValue;

	@Column
	private LocalDate emdReceivedOn;

	@Column
	private Double additionalEmd;

	@Column
	private String gstNo;

	@Column
	private String gstReturnType;

	@Convert(converter = ListConverter.class)
	private List<String> godownName;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "contractor")
	private List<ContractorChargesData> chargesData;

//	EMD Refund
	@Column
	private String emdRefundFor;

	@Column
	private Double refundAmount;

	@Column
	private String chequeVoucherJvNo;

	@Column
	private LocalDate date;

	@Column
	private String blocklist;

	@Column
	private String remarks;

	@Column
	private String status = "Active";

// additional godown
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "contractor")
	private List<ReassignedGodown> additionalGodownData;

	@Column
	private String filenameAgreement;

	@Column
	private String filetypeAgreement;

	@Lob
	@Column(length = 1000000)
	private byte[] filedataAgreement;

	@Column
	private String filenameRate;

	@Column
	private String filetypeRate;

	@Lob
	@Column(length = 1000000)
	private byte[] filedataRate;
}
