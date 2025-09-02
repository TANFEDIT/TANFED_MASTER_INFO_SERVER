package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.BeneficiaryMaster;
@Repository
public interface BeneficiaryMasterRepo extends JpaRepository<BeneficiaryMaster, Long> {

	public List<BeneficiaryMaster> findByOfficeName(String officeName);
	
	public List<BeneficiaryMaster> findByBeneficiaryNameAndOfficeName(String beneficiaryName, String officeName);
}
