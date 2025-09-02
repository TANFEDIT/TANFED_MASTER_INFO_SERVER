package com.tanfed.basicInfo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanfed.basicInfo.entity.LicenseData;
@Repository
public interface LicenseRepo extends JpaRepository<LicenseData, Long> {

	public LicenseData findByLicenseNumber(String LicenseNumber);
	
	public List<LicenseData> findByOfficeName(String officeName);
}
