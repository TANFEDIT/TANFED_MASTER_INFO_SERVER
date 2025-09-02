package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.BuyerFirmInfo;
@Repository
public interface BuyerFirmRepo extends JpaRepository<BuyerFirmInfo, Long> {

	public BuyerFirmInfo findByIfmsIdNo(String ifmsIdNo);
	
	public List<BuyerFirmInfo> findByOfficeName(String officeName);
}
