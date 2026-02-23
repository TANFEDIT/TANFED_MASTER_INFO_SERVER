package com.tanfed.basicInfo.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorStatusTable {

	private String contractThrough;
	private Double emdAmount;
	private LocalDate emdReceivedOn;
	private LocalDate emdRefundOn;
	private String contractFirm; 
	private String gstNo;
	private List<LocalDate> validityFrom;
	private List<LocalDate> validityTo;
	private List<String> hoIrRcno;
	private List<LocalDate> hoLetterDate;
	private String contractApproval;
	private String status;
	private Long dataId;
	private List<String> godownList;
	private List<String> additionalGodownList;
	private String filenameAgreement;
	private String filetypeAgreement;
	private byte[] filedataAgreement;
	private String filenameRate;
	private String filetypeRate;
	private byte[] filedataRate;
}
