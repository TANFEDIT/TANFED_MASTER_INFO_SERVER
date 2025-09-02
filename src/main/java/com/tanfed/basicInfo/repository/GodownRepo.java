package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.GodownInfo;

@Repository
public interface GodownRepo extends JpaRepository<GodownInfo, Long> {

	public GodownInfo findByGodownName(String godownName);
	
	public List<GodownInfo> findByOfficeName(String officeName);
}
