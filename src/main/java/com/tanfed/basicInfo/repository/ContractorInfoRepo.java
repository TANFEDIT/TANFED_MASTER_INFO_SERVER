package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.ContractorInfo;
@Repository
public interface ContractorInfoRepo extends JpaRepository<ContractorInfo, Long> {

	public List<ContractorInfo> findByContractFirm(String contractFirm);
	
	public List<ContractorInfo> findByOfficeName(String officeName);
}
