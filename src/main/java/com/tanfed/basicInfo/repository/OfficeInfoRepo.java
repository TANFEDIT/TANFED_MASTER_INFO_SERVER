package com.tanfed.basicInfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.OfficeInfo;
@Repository
public interface OfficeInfoRepo extends JpaRepository<OfficeInfo, Long> {

	public OfficeInfo findByOfficeName(String officeName);
}
