package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.BankInfo;
@Repository
public interface BankRepo extends JpaRepository<BankInfo, Long> {

	public List<BankInfo> findByOfficeName(String officeName);

	public BankInfo findByAccountNumber(Long accountNumber);
	
	
}
